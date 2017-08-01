name := "ppbot"

version := "1.0"

scalaVersion := "2.12.1"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.0.9",
  "de.heikoseeberger" %% "akka-http-play-json" % "1.17.0",
  "com.typesafe.play" %% "play-json" % "2.6.0"
)