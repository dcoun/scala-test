name := "ScalaTest"

version := "1.0"

scalaVersion := "2.11.11"

libraryDependencies ++= {
  Seq(
    "org.apache.logging.log4j" % "log4j-api" % "2.8.2",
    "org.apache.logging.log4j" % "log4j-core" % "2.8.2",

    "net.liftweb" % "lift-json_2.11" % "3.1.0",

    "org.specs2" % "specs2-core_2.11" % "3.9.4" % Test,
    "org.scalatest" % "scalatest_2.11" % "3.0.3" % Test
  )
}

resolvers ++= Seq(
  "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
)

// mainClass := Some("Application")

//sourceDirectory in Compile <<= baseDirectory(_ / "src")
//sourceDirectory in Test <<= baseDirectory(_ / "src/test/scala")

//resourceDirectory in Compile <<= baseDirectory(_ / "src/main/resources")
//resourceDirectory in Test := baseDirectory.value / "src/test/resources"
//unmanagedClasspath in Test += baseDirectory.value / "resources"

Keys.fork in Test := false
parallelExecution in Test := false

conflictManager := ConflictManager.latestRevision