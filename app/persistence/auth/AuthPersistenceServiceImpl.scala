package persistence.auth

import scala.slick.driver.SQLiteDriver.simple._
import scala.slick.driver.SQLiteDriver

class AuthPersistenceServiceImpl(override val database: Database) extends AuthPersistenceService {
  override val dal: AuthDal = new AuthDal(SQLiteDriver)
}
