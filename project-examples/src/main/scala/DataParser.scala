import file.{FileReader, FileWriter}

import scala.collection._
import net.liftweb.json._

import scala.util.matching.Regex

class DataParser {
  val fileReader = new FileReader(PathUtils.getProjectAbsolutePath("src/main/resources/txt_mini"))
  val fileWriter = new FileWriter(PathUtils.getProjectAbsolutePath("src/main/resources/result.json"))

  //  val pattern = """(\d+_\d+_(.*)) column=(data|host|refine):(.+), timestamp=(\d+), value=(.*)""".r
  val pattern =
    """(\d+)_(\d+)_(.*) column=data:(.+), timestamp=\d+, value=(.*)""".r

  def run(): Unit = {
    implicit val formats = DefaultFormats
    try {
      val lines = fileReader.getLines()
      val datas: mutable.Map[String, Any] = new mutable.HashMap[String, Any]
      var previousRowKey: String = ""
      var isFirst: Boolean = true

      fileWriter.writeLine("[")

      for (line <- lines) {
        pattern.findAllIn(line).matchData foreach (
          (m) => {
            //            LogUtil.info(m.group(5))
            val data = new DataModel(m)

            if (isFirst) {
              isFirst = false

              previousRowKey = data.getRowKey()
              datas += data.getKeyValue()
            } else if (previousRowKey.equals(data.getRowKey())) {
              datas += data.getKeyValue()
            } else {
              val datasJson = Serialization.write(datas.toMap)

              fileWriter.writeLine("{" + "\"rowKey\"" + ":" + "\"" + previousRowKey + "\"," + "\"data\"" + ":" + datasJson + "},")
              datas.clear()

              previousRowKey = data.getRowKey()
              datas += data.getKeyValue()
            }
          })
      }

      fileWriter.writeLine("]")
    } finally {
      fileReader.close()
      fileWriter.close()
    }
  }

  case class DataModel(m: Regex.Match) {
    val date: String = m.group(1)
    val timestamp: String = m.group(2)
    val hostName: String = m.group(3)
    val key: String = m.group(4)
    val value: String = m.group(5)

    def getRowKey(): String = {
      this.date + "_" + this.timestamp + "_" + this.hostName
    }

    def getKeyValue(): (String, Any) = {
      try {
        return (this.key -> parse(value))
      } catch {
        case e: Throwable =>
      }

      (this.key -> value)
    }
  }

}

/**
  * [
  * {
  * rowkey: "rowkey",
  * data: {
  * config: {},
  * event: "response",
  * headers: "...",
  * httpVersion: "",
  * }
  * },
  * ]
  */