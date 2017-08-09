// intellij sbt plugin
resolvers += "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"

if (java.lang.System.getProperty("idea.runid", "false") == "7.0.0+49-9bc0cd21") {
  scala.collection.Seq(
    addSbtPlugin("org.jetbrains" % "sbt-structure-extractor" % "7.0.0+49-9bc0cd21"),
    addSbtPlugin("org.jetbrains" % "sbt-idea-shell" % "1.2+2-3eadcace"),
    addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.6.0")
  )
} else {
  scala.collection.Seq.empty
}

//resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"
//addSbtPlugin("com.typesafe.sbt" %% "sbt-native-packager" % "0.7.2")