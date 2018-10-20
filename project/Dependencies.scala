import sbt._

object Dependencies {
  val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"
  val telegramCore = "com.bot4s" %% "telegram-core" % "4.0.0-RC1"
  val telegramAkka = "com.bot4s" %% "telegram-akka" % "4.0.0-RC1"
  val lightbend = "com.typesafe" % "config" % "1.3.2"
  val botDependencies: Seq[sbt.ModuleID] = Seq(scalaTest % Test, telegramAkka, telegramCore, lightbend)
}