package persistence.auth

import com.google.inject.Inject

import scala.slick.driver.SQLiteDriver.simple._
import scala.slick.driver.SQLiteDriver

class AuthPersistenceServiceImpl @Inject() (override val database: Database) extends BaseAuthPersistenceService {
  override val dal: AuthDal = new AuthDal(SQLiteDriver)
}
