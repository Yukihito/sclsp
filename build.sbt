name := "sclsp"

version := "0.2"

scalaVersion := "2.12.7"

organization := "com.yukihitoho"

libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.1"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"
scalacOptions ++= Seq("-deprecation", "-feature")
