
import utils.LogUtils

object Application extends App {
  def start(): Unit = {
    println("test!")
    LogUtils.info("Start Main!")
    LogUtils.info("Goodbye World!")

    // import scala.io.Source
    // val resources = Source.fromURL(getClass.getResource("resources.properties"))
    // LogUtils.info(resources)
  }

  override def main(args: Array[String]): Unit = {
    this.start()
  }
}
