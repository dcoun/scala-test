package macros

import scala.reflect.macros._
import scala.language.experimental.macros
import scala.annotation.{StaticAnnotation, compileTimeOnly}

object MacroAnnotation {
  def createMethodImpl(c: blackbox.Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._
    val result = {
      annottees.map(_.tree).toList match {
        case ModuleDef(mods, name, Template(parents, self, body)) :: Nil =>
          val helloMethod = DefDef(NoMods, TermName("hello"), List(), List(List()), TypeTree(), Literal(Constant("hello")))
          ModuleDef(mods, name, Template(parents, self, body :+ helloMethod))
      }
    }
    c.Expr[Any](result)
  }

  def transformAnnottees(c: blackbox.Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    println("Annotation macro")
    c.Expr[Any](annottees.head.tree)
  }
}

@compileTimeOnly("scalaplayground.macros not expanded")
class SimpleMacroAnnotation extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro MacroAnnotation.createMethodImpl
}

@compileTimeOnly("scalaplayground.macros not expanded")
class SimpleMacroAnnotation2 extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro MacroAnnotation.transformAnnottees
}
