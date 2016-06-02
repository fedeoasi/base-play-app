name := """base-play-app"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

lazy val securesocial = "ws.securesocial" %% "securesocial" % "3.0-M4"

lazy val webJarsBootstrap = "org.webjars" % "bootstrap" % "3.1.1-2"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  filters,
  securesocial,
  webJarsBootstrap,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

includeFilter in (Assets, LessKeys.less) := "*.less"
excludeFilter in (Assets, LessKeys.less) := "_*.less"
LessKeys.compress := false
LessKeys.verbose := true
