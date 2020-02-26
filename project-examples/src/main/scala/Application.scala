
import scala.io.Source

object Application {
  def start(): Unit = {
    println("test!")
    LogUtils.info("Start Main!")
    LogUtils.info("Goodbye World!")

    val resources = Source.fromURL(getClass.getResource("resources.properties"))
    LogUtils.info(resources)
  }

  def main(args: Array[String]): Unit = {
    this.start()

    new DataParser().run()
  }
}
