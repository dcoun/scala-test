import util.LogUtil

import scala.io.Source

object Application {
  def start(): Unit = {
    println("test!")
    LogUtil.info("Start Main!")
    LogUtil.info("Goodbye World!")

    val resources = Source.fromURL(getClass.getResource("resources.properties"))
    LogUtil.info(resources)
  }

  def main(args: Array[String]): Unit = {
    this.start()

    new DataParser().run()
  }
}
