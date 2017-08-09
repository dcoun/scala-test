package file

import java.io.IOException

import scala.collection.Iterator
import scala.io.Source

class FileReader(filePath: String) {
  val source = Source.fromFile(filePath)

  @throws[IOException]
  def getLines(): Iterator[String] = {
    source.getLines()
  }

  @throws[IOException]
  def close(): Unit = {
    source.close()
  }
}
