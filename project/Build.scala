import sbt._

object Build {
  val scalaVersion = "2.11.12"

  lazy val loggerExclusionRules: Seq[ExclusionRule] = Seq(
    ExclusionRule("org.slf4j", "slf4j-api"),
    ExclusionRule("org.slf4j", "slf4j-log4j12"),
    ExclusionRule("commons-logging", "commons-logging"),
    ExclusionRule("org.apache.logging.log4j", "log4j-api"),
    ExclusionRule("org.apache.logging.log4j", "log4j-core"),
    ExclusionRule("org.apache.logging.log4j", "log4j-slf4j-impl"),
    //    ExclusionRule("log4j", "log4j")
    )

  val slf4jVersion = "1.7.26"
  val log4jVersion = "2.11.2"
  lazy val logger: Seq[ModuleID] = Seq(
    "org.slf4j" % "slf4j-api" % this.slf4jVersion,
    "org.apache.logging.log4j" % "log4j-api" % this.log4jVersion,
    "org.apache.logging.log4j" % "log4j-core" % this.log4jVersion,
    "org.apache.logging.log4j" % "log4j-slf4j-impl" % this.log4jVersion
    )

  lazy val testCore: Seq[ModuleID] = Seq(
    "org.specs2" % "specs2-core_2.11" % "3.9.4" % Test,
    "org.specs2" % "specs2-junit_2.11" % "3.9.4" % Test
    )

  val playJsonVersion = "2.6.11"
  lazy val playJsonCore: Seq[ModuleID] = Seq(
    "com.typesafe.play" % "play-json_2.11" % this.playJsonVersion excludeAll (Seq(
      ExclusionRule("com.fasterxml.jackson.core", "jackson-annotations"),
      ExclusionRule("com.fasterxml.jackson.core", "jackson-core"),
      ExclusionRule("com.fasterxml.jackson.core", "jackson-databind"),
      ExclusionRule("com.fasterxml.jackson.dataformat"),
      ExclusionRule("com.fasterxml.jackson.datatype")
      ): _*)
    )

  lazy val commonDependencies: Seq[ModuleID] = {
    val dependencies = Seq(
      "com.typesafe" % "config" % "1.3.3",

      "org.apache.httpcomponents" % "httpclient" % "4.5.5",
      "org.apache.httpcomponents" % "httpcore" % "4.4.9",

      "joda-time" % "joda-time" % "2.9.9"
      )

    this.logger ++
      this.testCore ++
      dependencies
  }
}
