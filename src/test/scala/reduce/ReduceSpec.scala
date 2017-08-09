package reduce

import org.specs2.mutable._

class ReduceSpec extends Specification {
  "Reduce" should {

    "return 3 at [1, 2]" in {
      Reduce.sum(Array(1, 2)) mustEqual 3
    }

    "return 6 at [1, 2, 3]" in {
      Reduce.sum(Array(1, 2, 3)) mustEqual 6
    }
  }
}
