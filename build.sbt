name := """dSport-Backend"""
organization := "OMGP"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies += guice

libraryDependencies ++= Seq(
  javaJdbc,
  ehcache,
  javaWs
)

libraryDependencies += "org.neo4j" % "neo4j-ogm-core" % "3.0.0-RC1"
libraryDependencies += "org.neo4j" % "neo4j-ogm-bolt-driver" % "3.0.0-RC1"

libraryDependencies += "com.auth0" % "java-jwt" % "3.2.0"

libraryDependencies += "com.typesafe.play" %% "play-mailer" % "6.0.1"
libraryDependencies += "com.typesafe.play" %% "play-mailer-guice" % "6.0.1"














