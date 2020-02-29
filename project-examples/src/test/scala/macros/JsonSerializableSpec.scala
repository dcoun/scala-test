package macros

import testutils.Specification

import play.api.libs.json._
import scala.language.experimental.macros

class JsonSerializableSpec extends Specification {

  object T1 {

    trait Props {
      def string: String

      def int: Int

      def long: Long

      def double: Double

      def boolean: Boolean

      def opt: Option[String]

      def list: List[String]

      def map: Map[String, String]

      def jsValue: JsValue

      def jsObject: JsObject

      def jsArray: JsArray

      def nil: JsValue
    }

  }

  @JsonSerializable("JsonField")
  class T1 extends T1.Props {
    @JsonField var string: String = ""
    @JsonField var int: Int = 0
    @JsonField var long: Long = 0L
    @JsonField var double: Double = 0F
    @JsonField var boolean: Boolean = false
    @JsonField var opt: Option[String] = Some("as")
    @JsonField var list: List[String] = List()
    @JsonField var map: Map[String, String] = Map()
    @JsonField var jsValue: JsValue = JsFalse
    @JsonField var jsObject: JsObject = Json.obj()
    @JsonField var jsArray: JsArray = Json.arr()
    @JsonField var nil: JsValue = JsNull

    @JsonField def b: Boolean = boolean
  }

  @JsonSerializable
  class T2(
    // @JsonField _private: String = "",
    @JsonField val _val: String = "",
    @JsonField var _var: String
  ) {
    @JsonField var boolean: Boolean = false

    @JsonField val immutable: Boolean = boolean

    @JsonField def func1: Boolean = boolean

    def func2: Boolean = boolean
  }

  "JsonSerializeable" should {
    val t1 = new T1()

    "with t1 json" in {
      Json.toJson(t1) mustEqual Json.obj(
        "string" -> t1.string,
        "int" -> t1.int,
        "long" -> t1.long,
        "double" -> t1.double,
        "boolean" -> t1.boolean,
        "opt" -> t1.opt,
        "list" -> t1.list,
        "map" -> t1.map,
        "jsValue" -> t1.jsValue,
        "jsObject" -> t1.jsObject,
        "jsArray" -> t1.jsArray,
        "nil" -> t1.nil,
        "b" -> t1.b
        )
    }

    "with t1 json parsed" in {
      val parsed = Json.toJson(t1).as[T1]

      parsed.string mustEqual t1.string
      parsed.int mustEqual t1.int
      parsed.long mustEqual t1.long
      parsed.double mustEqual t1.double
      parsed.boolean mustEqual t1.boolean
      parsed.opt mustEqual t1.opt
      parsed.list mustEqual t1.list
      parsed.map mustEqual t1.map
      parsed.jsValue mustEqual t1.jsValue
      parsed.jsObject mustEqual t1.jsObject
      parsed.jsArray mustEqual t1.jsArray
      parsed.nil mustEqual t1.nil
      parsed.b mustEqual t1.b
    }

    val t2 = new T2(_var = "asdf")
    "with t2 json" in {
      println(t2)
      Json.toJson(t2) mustEqual Json.obj(
        "_val" -> t2._val,
        "_var" -> t2._var,
        "boolean" -> t2.boolean,
        "immutable" -> t2.immutable,
        "func1" -> t2.func1
        )
    }

    "with t2 json parsed" in {
      val parsed = Json.toJson(t2).as[T2]
      parsed._val mustEqual t2._val
      parsed._var mustEqual t2._var
      parsed.boolean mustEqual t2.boolean
      parsed.immutable mustEqual t2.immutable
      parsed.func1 mustEqual t2.func1
    }
  }
}
