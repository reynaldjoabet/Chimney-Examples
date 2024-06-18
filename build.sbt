// The simplest possible sbt build file is just one line:

name    := "Chimney-Examples"
version := "1.0"

val chimney = "io.scalaland" %% "chimney" % "0.8.4"

lazy val `scala2-examples` = (project in file("scala2-examples")).settings(
  scalaVersion         := "2.13.12",
  libraryDependencies ++= Seq(chimney)
)

lazy val `scala3-examples` = (project in file("scala3-examples")).settings(
  scalaVersion         := "3.3.1",
  libraryDependencies ++= Seq(chimney),
  scalacOptions ++= Seq(
    "-no-indent"
  )
)

//fork in run := true

ThisBuild / semanticdbEnabled := true

ThisBuild/ usePipelining := true
