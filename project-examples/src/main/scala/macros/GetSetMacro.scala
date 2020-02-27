package macros

import scala.reflect.macros._
import scala.language.experimental.macros
import scala.annotation.{StaticAnnotation, compileTimeOnly}

object GetSetMacroObject {
  def getSetImpl(c: blackbox.Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._

    def extractClassNameAndFields(classDecl: ClassDef) = {
      try {
        val q"class $className(..$fields) extends ..$bases { ..$body }" = classDecl
        (className, fields)
      } catch {
        case _: MatchError => c.abort(c.enclosingPosition, "Annotation is only supported on case class")
      }
    }

    def modifiedDeclaration(classDecl: ClassDef, compDeclOpt: Option[ModuleDef] = None) = {
      val (className, fields) = extractClassNameAndFields(classDecl)
      println(className, fields)
      println(classDecl)
      //      val format = jsonFormatter(className, fields)
      //      val compDecl = modifiedCompanion(compDeclOpt, format, className)

      // Return both the class and companion object declarations
      c.Expr(q"""$classDecl""")
    }

    annottees.map(_.tree) match {
      case ClassDef(mods, name, tparams, Template(parents, self, body)) :: Nil =>
        println("ClassDef")
        println(s"mods: $mods")
        println(s"name: $name")
        println(s"tparams: $tparams")
        println(s"parents: $parents")
        println(s"self: $self")
        println()
        println("body")
        body.foreach(println)
        c.Expr[Any](q"""${ClassDef(mods, name, tparams, Template(parents, self, body))}""")

      case ValDef(mods, name, tpt, rhs) :: Nil =>
        val variableName = name.toString
        val getterName = TermName(s"$variableName")
        val setterName = TermName(s"$variableName" + "_$eq")
        // def $name: $tpt = getDataValues($name)
        // def $name_=(newVal: $tpt): Unit = setDataValues($name, newVal)
        c.Expr[Any](
          q"""
              def $getterName: $tpt = getDataValues($variableName)
              def $setterName(newVal: $tpt): Unit = setDataValues($variableName, newVal)
            """)

      case (classDecl: ClassDef) :: (compDecl: ModuleDef) :: Nil =>
        c.Expr[Any](
          q"""
           $classDecl
           $compDecl
         """)
      case e => println(e)
        c.abort(c.enclosingPosition, "Invalid annottee")
    }
  }
}

@compileTimeOnly("scalaplayground.macros not expanded")
class GetSetMacro extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro GetSetMacroObject.getSetImpl
}

