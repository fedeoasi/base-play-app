package modules

import com.google.inject.AbstractModule
import persistence.SQLiteDatabaseInitializer
import persistence.auth.{AuthPersistenceService, AuthPersistenceServiceImpl}

import scala.slick.driver.JdbcDriver.simple._

class Module extends AbstractModule {

  override def configure() = {
    val database = SQLiteDatabaseInitializer.database("base-play-app")
    bind(classOf[Database]).toInstance(database)
    bind(classOf[AuthPersistenceService]).toInstance(new AuthPersistenceServiceImpl(database))
  }

}
