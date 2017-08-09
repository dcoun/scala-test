package util

import org.apache.logging.log4j.{LogManager, Logger}

object LogUtil {
  private val logger: Logger = LogManager.getLogger("App")

  def info(message: AnyRef): Unit = {
    logger.info(message)
  }

  def debug(message: AnyRef): Unit = {
    logger.debug(message)
  }

  def warn(message: AnyRef, t: Throwable*): Unit = {
    if (!t.isEmpty) {
      logger.warn(message, t.asInstanceOf[Throwable])
    } else {
      logger.warn(message)
    }
  }

  def error(message: AnyRef, t: Throwable*): Unit = {
    if (!t.isEmpty) {
      logger.error(message, t.asInstanceOf[Throwable])
    } else {
      logger.error(message)
    }
  }
}
