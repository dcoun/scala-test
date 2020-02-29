package utils

import scala.language.experimental.macros
import scala.reflect.macros._

object MacroTypeChecker {
  def findTreeType(c: blackbox.Context)(tree: c.Tree): Unit = {
    import c.universe._
    tree match {
      case t: RefTree => println(s"RefTree: $t")
      case t: PackageDef => println(s"PackageDef: $t")
      case t: ClassDef => println(s"ClassDef: $t")
      case t: ModuleDef => println(s"ModuleDef: $t")
      case t: ValDef => println(s"ValDef: $t")
      case t: DefDef => println(s"DefDef: $t")
      case t: TypeDef => println(s"TypeDef: $t")
      case t: LabelDef => println(s"LabelDef: $t")
      case t: ImportSelector => println(s"ImportSelector: $t")
      case t: Import => println(s"Import: $t")
      case t: Template => println(s"Template: $t")
      case t: Block => println(s"Block: $t")
      case t: CaseDef => println(s"CaseDef: $t")
      case t: Alternative => println(s"Alternative: $t")
      case t: Star => println(s"Star: $t")
      case t: Bind => println(s"Bind: $t")
      case t: UnApply => println(s"UnApply: $t")
      case t: Function => println(s"Function: $t")
      case t: Assign => println(s"Assign: $t")
      case t: AssignOrNamedArg => println(s"AssignOrNamedArg: $t")
      case t: If => println(s"If: $t")
      case t: Match => println(s"Match: $t")
      case t: Return => println(s"Return: $t")
      case t: Try => println(s"Try: $t")
      case t: Throw => println(s"Throw: $t")
      case t: New => println(s"New: $t")
      case t: Typed => println(s"Typed: $t")
      case t: TypeApply => println(s"TypeApply: $t")
      case t: Apply => println(s"Apply: $t")
      case t: Super => println(s"Super: $t")
      case t: This => println(s"This: $t")
      case t: Select => println(s"Select: $t")
      case t: Ident => println(s"Ident: $t")
      case t: Literal => println(s"Literal: $t")
      case t: Annotated => println(s"Annotated: $t")
      case t: SingletonTypeTree => println(s"SingletonTypeTree: $t")
      case t: SelectFromTypeTree => println(s"SelectFromTypeTree: $t")
      case t: CompoundTypeTree => println(s"CompoundTypeTree: $t")
      case t: AppliedTypeTree => println(s"AppliedTypeTree: $t")
      case t: TypeBoundsTree => println(s"TypeBoundsTree: $t")
      case t: ExistentialTypeTree => println(s"ExistentialTypeTree: $t")
      case t: TypeTree => println(s"TypeTree: $t")
      case t => println(s"Unknown: $t")
    }
  }
}
