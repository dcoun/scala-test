package macros

import scala.reflect.macros._
import scala.language.experimental.macros
import scala.annotation.StaticAnnotation
import scala.annotation.compileTimeOnly

/** https://docs.scala-lang.org/overviews/macros/annotations.html */

@compileTimeOnly("enable macro paradise to expand macro annotations")
class Identity extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro ???
}
