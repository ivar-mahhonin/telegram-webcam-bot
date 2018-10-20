import sbt._

object Dependencies {
  val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"
  val telegramCore = "com.bot4s" %% "telegram-core" % "4.0.0-RC1"
  val telegramAkka = "com.bot4s" %% "telegram-akka" % "4.0.0-RC1"
  val lightbend = "com.typesafe" % "config" % "1.3.2"
  val akkaHttp = "com.typesafe.akka" %% "akka-http" % "10.1.5"
  val akka = "com.typesafe.akka" %% "akka-stream" % "2.5.12"
  val botDependencies: Seq[sbt.ModuleID] = Seq(scalaTest % Test, telegramAkka, telegramCore, lightbend, akkaHttp, akka)
}