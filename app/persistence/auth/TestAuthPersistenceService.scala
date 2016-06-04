package persistence.auth

import java.util.UUID

import scala.slick.driver.H2Driver
import scala.slick.driver.SQLiteDriver.simple._

class TestAuthPersistenceService extends BaseAuthPersistenceService {
  val database = Database.forURL(s"jdbc:h2:mem:userapp" + UUID.randomUUID() + ";DB_CLOSE_DELAY=-1",
    driver = "org.h2.Driver")
  override val dal: AuthDal = new AuthDal(H2Driver)

  import dal._
  import driver.simple._

  def initializeDatabase() {
    database.withSession { implicit s =>
      dal.tables.foreach(_.ddl.create)
    }
  }

  initializeDatabase()
}
