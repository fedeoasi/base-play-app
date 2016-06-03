package persistence

trait Dal {
  this: Profile =>

  import driver.simple._

  def createStatements: Seq[String]
  def create(implicit session: Session): Unit
}
