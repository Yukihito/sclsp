name := "sclsp"

version := "0.1-SNAPSHOT"

scalaVersion := "2.12.7"

libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.1"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"
scalacOptions ++= Seq("-deprecation", "-feature")
