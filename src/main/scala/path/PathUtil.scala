package path

import java.io.File

object PathUtil {
  def getProjectAbsolutePath(paths: String*): String = {
    new File(paths.reduce(_ + "/" + _)).getAbsolutePath()
  }

  def getProjectAbsoluteFile(paths: String*): File = {
    new File(paths.reduce(_ + "/" + _)).getAbsoluteFile()
  }
}