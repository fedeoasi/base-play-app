package persistence

import persistence.auth.AuthDal

import scala.slick.driver.SQLiteDriver

object SqliteDdlPrinter {
  val driver = SQLiteDriver

  val dals = Seq(new AuthDal(driver))

  def main(args: Array[String]) {
    dals.foreach { d =>
      println(d.createStatements.mkString("\n"))
    }
  }
}
