version in ThisBuild := "0.1"

scalaVersion in ThisBuild := Build.scalaVersion
resolvers in ThisBuild ++= Seq(
  "CDH5 repository" at "https://repository.cloudera.com/artifactory/cloudera-repos/",
  "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases",
  "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/",
  "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/",
  Resolver.sonatypeRepo("snapshots")
  )
conflictManager in ThisBuild := sbt.ConflictManager.latestRevision

lazy val projectCommonSettings = Seq(
  maintainer := "dcoun08@gmail.com",

  logLevel := Level.Info,

  envVars ++= Map(
    "VERSION" -> version.value,
    "TITLE" -> name.value
    ),
  javaOptions ++= Seq(),
  packageOptions += Package.ManifestAttributes(
    "Implementation-Title" -> name.value,
    "Implementation-Version" -> (version in ThisBuild).value
    ),

  envVars in Test ++= Map(
    "MODE" -> "test"
    ),
  fork in Test := true,
  parallelExecution in Test := false
  )

lazy val utils = (project in file("project-utils"))
  .settings(projectCommonSettings: _*)
  .settings(name := "utils")
  .settings(libraryDependencies ++= Build.commonDependencies)

lazy val examples = (project in file("project-examples"))
  .settings(projectCommonSettings: _*)
  .dependsOn(utils % "compile->compile;test->test;")
  .aggregate(utils)
  .settings(name := "examples")
  .settings(libraryDependencies ++= Build.commonDependencies)