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

val ideaVersion = java.lang.System.getProperty("idea.runid", "false")
ideaVersion match {
  case null => Seq.empty
  case _ => Seq(
    addSbtPlugin("org.jetbrains" % "sbt-idea-plugin" % "3.5.0"))
}
