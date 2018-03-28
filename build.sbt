name := """base-play-app"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

lazy val securesocial = "ws.securesocial" %% "securesocial" % "3.0-M6"

lazy val webJarsBootstrap = "org.webjars" % "bootstrap" % "3.1.1-2"

lazy val slick = "com.typesafe.slick" %% "slick" % "2.1.0"
lazy val sqlite = "org.xerial" % "sqlite-jdbc" % "3.8.7"
lazy val jodaMapper = "com.github.tototoshi" %% "slick-joda-mapper" % "1.2.0"
lazy val h2 = "com.h2database" % "h2" % "1.4.185"
lazy val flyway = "org.flywaydb" % "flyway-core" % "3.2.1"
lazy val dbDependencies = Seq(slick, sqlite, jodaMapper, flyway)

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  filters,
  securesocial,
  webJarsBootstrap,
  "org.scalatestplus" %% "play" % "1.4.0" % Test
) ++ dbDependencies

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

includeFilter in (Assets, LessKeys.less) := "*.less"
excludeFilter in (Assets, LessKeys.less) := "_*.less"
LessKeys.compress := false
LessKeys.verbose := true
