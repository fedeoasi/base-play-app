package modules

import java.time.Clock

import com.google.inject.AbstractModule
import persistence.SQLiteDatabaseInitializer
import persistence.auth.{AuthPersistenceService, AuthPersistenceServiceImpl}
import services.ApplicationTimer

import scala.slick.driver.JdbcDriver.simple._

/**
 * This class is a Guice module that tells Guice how to bind several
 * different types. This Guice module is created when the Play
 * application starts.
  *
  * Play will automatically use any class called `modules.Module` that is in
 * the root package. You can create modules in other locations by
 * adding `play.modules.enabled` settings to the `application.conf`
 * configuration file.
 */
class Module extends AbstractModule {

  override def configure() = {
    val database = SQLiteDatabaseInitializer.database("courses")

    bind(classOf[Database]).toInstance(database)
    bind(classOf[AuthPersistenceService]).toInstance(new AuthPersistenceServiceImpl(database))
    // Use the system clock as the default implementation of Clock
    bind(classOf[Clock]).toInstance(Clock.systemDefaultZone)
    // Ask Guice to create an instance of ApplicationTimer when the
    // application starts.
    bind(classOf[ApplicationTimer]).asEagerSingleton()
  }

}
