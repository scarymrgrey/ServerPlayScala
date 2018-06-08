name := """PlayServer"""
organization := "scary"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.6"


libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies ++= {
  val liftVersion = "3.2.0"
  val lift_json = "net.liftweb" %% "lift-json" % "2.6"
  Seq(
    "org.mongodb" %% "casbah" % "3.1.1",
    "com.github.salat" %% "salat" % "1.11.2",
    "org.scala-lang.modules" %% "scala-async" % "0.9.7",
    "com.github.fakemongo" % "fongo" % "2.1.0",
    "net.liftweb"       %% "lift-webkit" % liftVersion % "compile",
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "org.scala-lang" % "scala-reflect" % "2.12.6",
    "io.spray" %%  "spray-json" % "1.3.3",
    "com.fasterxml.jackson.core" % "jackson-databind" % "2.7.3",
    "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.7.2",
    "org.json4s" %% "json4s-native" % "3.5.0",
    "org.json4s" %% "json4s-jackson" % "3.5.0"
  )
}

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "scary.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "scary.binders._"
