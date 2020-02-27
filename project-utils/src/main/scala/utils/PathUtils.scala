package utils

import java.io.File

object PathUtils {
  def getProjectAbsolutePath(paths: String*): String = {
    new File(paths.reduce(_ + "/" + _)).getAbsolutePath
  }

  def getProjectAbsoluteFile(paths: String*): File = {
    new File(paths.reduce(_ + "/" + _)).getAbsoluteFile
  }
}
