ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.11"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.16" % "test"

libraryDependencies += "org.scalatestplus" %% "mockito-4-11" % "3.2.16.0" % "test"

lazy val root = (project in file("."))
  .settings(
    name := "OnlineLesson1"
  )
