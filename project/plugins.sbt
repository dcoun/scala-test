resolvers += "Sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

/**
 * show dependency graph
 * sbt -no-colors "dependencyTree" > dependencyFile.log
 * sbt -no-colors "common / dependencyTree" > dependencyFile.log
 * sbt -no-colors "evicted" > evictedFile.log
 */
addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.9.2")

/** intellij sbt plugin */
addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.3.15")

java.lang.System.getProperty("idea.runid", "false") match {
  case "2017.2" => scala.collection.Seq(
    addSbtPlugin("org.jetbrains" % "sbt-structure-extractor" % "2017.2"),
    addSbtPlugin("org.jetbrains" % "sbt-idea-shell" % "2017.2"))
  case "2018.2" => scala.collection.Seq(
    addSbtPlugin("org.jetbrains" % "sbt-structure-extractor" % "2018.2"),
    addSbtPlugin("org.jetbrains" % "sbt-idea-shell" % "2017.2"))
  case _ => scala.collection.Seq.empty
}
