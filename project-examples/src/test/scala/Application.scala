import org.specs2.mutable._

class ApplicationSpec extends Specification {
  "Application" should {

    "The 'Goodbye World!' string" should {
      "contain 14 characters" in {
        "Goodbye World!" must have size (14)
      }

      "start with 'Goodbye'" in {
        "Goodbye World!" must startWith("Goodbye")
      }

      "end with 'World!'" in {
        "Goodbye World!" must endWith("World!")
      }
    }
  }
}
