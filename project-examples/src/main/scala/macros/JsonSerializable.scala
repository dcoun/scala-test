package macros

import utils.LogUtils

import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.language.experimental.macros
import scala.reflect.macros._

/**
 * 아 지옥이다..
 *
 * json.Format readers, writers 오버라이딩으로 구현
 *  - apply, unapply 필요없음
 *  - validation 불가
 *
 * 기본 json.Format 사용
 *  - case class가 아니면 apply, unapply 구현 필요
 *  - validation 코드 삽입이 깔끔 + play validation interface 그대로 사용 가능
 *  - lazy read 필요하면 대환장 파티
 *
 * case class 사용
 *  - 나도 이러고 싶다
 *  - GetSet 달리면 class로 재정의...? (미친듯)
 *
 * TODO: accessor에 private[this] 이면 오류 => 런타임미러 써야하는데 흐음..
 */
object JsonSerializableMacro extends LogUtils.Loggable {
  def impl(c: blackbox.Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._

    /** fields, readable, writable */
    type FieldDictionary = (Seq[ValOrDefDef], Seq[ValOrDefDef], Seq[ValOrDefDef])

    val identifier = {
      val default: Seq[String] = Seq(JsonField.getClass.getSimpleName.replace("$", ""))
      if (c.prefix.tree.children.length > 1) {
        val last = c.prefix.tree.children.last.toString.replace("\"", "")
        (default :+ last).distinct
      } else {
        default
      }
    }
    Log.trace(s"JsonField identifiers: ${identifier.mkString(", ")}")

    def getJsonFields(classDecl: ClassDef): FieldDictionary = {
      val fields = classDecl.impl.body.foldLeft(Seq[ValOrDefDef]())({
        case (result, next: ValOrDefDef) =>
          if (next.mods.annotations.exists({
            case Apply(RefTree(New(RefTree(_, name)), _), _) => identifier.contains(name.toString)
          })) result :+ next
          else result
      })

      fields.length match {
        case 0 => c.abort(c.enclosingPosition, "Cannot create JsonFormat with empty JsonField")
        case _ => (
          fields,
          fields.filter({
            case valDef: ValDef => valDef.mods.hasFlag(Flag.MUTABLE) || valDef.mods.hasFlag(Flag.PARAMACCESSOR)
            case _ => false
          }),
          fields.filter(!_.mods.hasFlag(Flag.PRIVATE))
        )
      }
    }

    def defApply(className: TypeName, field: FieldDictionary): Tree = {
      val accessor = field._2.filter(_.mods.hasFlag(Flag.PARAMACCESSOR))
        .map(p => ValDef(NoMods, p.name, p.tpt, p.rhs))
      val member = field._2.filter(!_.mods.hasFlag(Flag.PARAMACCESSOR))
        .map(p => ValDef(NoMods, p.name, p.tpt, p.rhs))

      q"""
         def apply(..$accessor, ..$member): $className = {
            val obj = new $className(..${accessor.map(_.name)})
             ..${member.map(p => q"obj.${p.name} = ${p.name}")}
            obj
         }
       """
    }

    def defUnapply(className: TypeName, field: FieldDictionary): Tree = {
      q"""
         def unapply(arg: $className): Option[(..${field._3.map(p => q"${p.tpt}")})] =
           if (arg eq null) None
           else Some((..${field._3.map(p => q"arg.${p.name}")}))
       """
    }

    // TODO: read에서 defaultValue(rhs)
    def defFormat(className: TypeName, field: FieldDictionary): Tree = {
      def isOption(tpt: Tree): Boolean = tpt.toString.contains("Option")

      def fold(result: Tree, decl: Tree): Tree = {
        if (result.isEmpty) decl
        else q"$result.and($decl)"
      }

      val readsDecl = field._2.foldLeft(q"") { (result, p) =>
        fold(result, {
          if (isOption(p.tpt))
            q"(__ \ ${p.name.toString}).readNullable[${p.tpt.asInstanceOf[AppliedTypeTree].args.head}]"
          else q"(__ \ ${p.name.toString}).read[${p.tpt}]"
        })
      }
      val reads = q"($readsDecl) (${className.toTermName}.apply _)"

      val writersDecl = field._3.foldLeft(q"") { (result, p) =>
        fold(result, {
          if (isOption(p.tpt))
            q"(__ \ ${p.name.toString}).writeNullable[${p.tpt.asInstanceOf[AppliedTypeTree].args.head}]"
          else q"(__ \ ${p.name.toString}).write[${p.tpt}]"
        })
      }
      val writers = q"($writersDecl) (unlift(${className.toTermName}.unapply))"

      q"""
        implicit val jsonFormat = {
          import play.api.libs.json._
          import play.api.libs.functional.syntax._
          Format(
            $reads,
            $writers
          )
        }
      """
    }

    def build(classDecl: ClassDef, compDeclOpt: Option[ModuleDef] = None): c.Expr[Nothing] = {
      val fields = getJsonFields(classDecl)
      val apply = defApply(classDecl.name, fields)
      val unapply = defUnapply(classDecl.name, fields)
      val format = defFormat(classDecl.name, fields)

      compDeclOpt match {
        case Some(compDecl) =>
          val q"object $compName extends ..$bases { ..$body }" = compDecl
          c.Expr(
            q"""
              object $compName extends ..$bases {
                $format
                ..$body
                $apply
                $unapply
              }
              $classDecl
            """)
        case None =>
          c.Expr(
            q"""
              object ${classDecl.name.toTermName} {
                $format
                $apply
                $unapply
              }
              $classDecl
            """)
      }
    }

    annottees.map(_.tree) match {
      case (classDecl: ClassDef) :: Nil => build(classDecl)
      case (classDecl: ClassDef) :: (compDecl: ModuleDef) :: Nil => build(classDecl, Some(compDecl))
      case e => c.abort(c.enclosingPosition, s"Invalid annottee: $e")
    }
  }
}

@compileTimeOnly("scalaplayground.macros not expanded")
class JsonSerializable(identifier: String = "JsonField") extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro JsonSerializableMacro.impl
}

case class JsonField() extends StaticAnnotation {

}
