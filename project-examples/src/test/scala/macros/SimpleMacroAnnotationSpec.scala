package macros

import testutils.Specification

class SimpleMacroAnnotationSpec extends Specification {

  @SimpleMacroAnnotation
  object T1 {
    println(this)
    println(this.hello)
  }

//  @SimpleMacroAnnotation
//  class T2 {
//    println(this)
//    println(this.hello)
//  }

  "Test" should {
    "with SimpleMacroAnnotation" in {
      T1.hello mustEqual "hello"
//      new T2().hello mustEqual "hello"
    }
  }
}
