package macros

import scala.reflect.macros._
import scala.language.experimental.macros

object ToClassDecl {
  def toClassDeclImpl(c: blackbox.Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._
    annottees.map(_.tree) match {
      case (classDecl: ClassDef) :: Nil => c.Expr(q"""$classDecl""")
      case e => println(e)
        c.abort(c.enclosingPosition, "Invalid annottee")
    }
  }
}
