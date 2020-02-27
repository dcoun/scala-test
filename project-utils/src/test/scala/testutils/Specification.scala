package testutils

import org.specs2.specification.{BeforeAfterAll, BeforeAfterEach}
import utils.LogUtils

trait Specification extends org.specs2.mutable.Specification
  with BeforeAfterEach
  with BeforeAfterAll
  with LogUtils.Loggable {
  sequential

  override def beforeAll: Unit = {}

  override def afterAll: Unit = {}

  override def before: Unit = {}

  override def after: Unit = {}
}
