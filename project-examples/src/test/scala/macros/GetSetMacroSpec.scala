package macros

import testutils.Specification

import scala.collection.mutable
import scala.language.experimental.macros

class GetSetMacroSpec extends Specification {

  class T1 {
    val data: mutable.Map[String, Any] = mutable.Map()

    @GetSetMacro
    var v: String = ""

    @GetSetMacro
    var vv: String = ""

    def getDataValues[T <: Any](key: String): T = {
      println(s"called getDataValues: $key")
      data(key).asInstanceOf[T]
    }

    def setDataValues[T](key: String, value: T): Unit = {
      println(s"called setDataValues: $key $value")
      data.put(key, value)
    }

    this.getClass
      .getMethods
      .filter(n => n.toString.contains("macros.GetSetMacroSpec$T1.v"))
      .foreach(println)
  }

  "Test" should {
    val t = new T1()

    "with GetSetMacro" in {
      t.v = "v"
      t.v mustEqual "v"
    }

    "te" in {
      t.vv = "vv2"
      t.vv mustEqual "vv2"
    }
  }
}
