package utils

import org.slf4j.{Logger, LoggerFactory}

object LogUtils {
  final val Log: Logger = LoggerFactory.getLogger(this.getClass)

  def info(message: Any): Unit = {
    Log.info(message.toString)
  }

  def debug(message: Any): Unit = {
    Log.debug(message.toString)
  }

  def warn(message: Any, t: Throwable*): Unit = {
    if (t.nonEmpty) {
      Log.warn(message.toString, t.asInstanceOf[Throwable])
    } else {
      Log.warn(message.toString)
    }
  }

  def error(message: Any, t: Throwable*): Unit = {
    if (t.nonEmpty) {
      Log.error(message.toString, t.asInstanceOf[Throwable])
    } else {
      Log.error(message.toString)
    }
  }

  trait Loggable {
    protected final val Log: Logger = LoggerFactory.getLogger(
      this.getClass.getName
        .replaceAll("\\$$", "")
        .replaceAll("\\$", ".")
      )
  }

}
