name := "delegate"

version := "0.2.0"

scalaVersion := "2.12.4"

crossScalaVersions := Seq("2.11.11", "2.12.4")

// https://mvnrepository.com/artifact/org.scala-lang/scala-reflect
libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value

resolvers += Resolver.sonatypeRepo("releases")
addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

initialCommands in console := """import org.glavo.delegate._"""