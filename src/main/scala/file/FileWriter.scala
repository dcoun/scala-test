package file

import java.io.{BufferedWriter, File, IOException}

class FileWriter(filePath: String) {
  val dest = new File(filePath)
  val writer = new BufferedWriter(new java.io.FileWriter(dest))

  @throws[IOException]
  def write(c: Int): Unit = {
    writer.write(c)
  }

  @throws[IOException]
  def write(str: String): Unit = {
    writer.write(str)
  }

  @throws[IOException]
  def writeLine(line: String): Unit = {
    writer.write(line)
    writer.newLine()
  }

  @throws[IOException]
  def close(): Unit = {
    writer.flush()
    writer.close()
  }

}
