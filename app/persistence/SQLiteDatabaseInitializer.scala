package persistence

import org.flywaydb.core.Flyway
import org.sqlite.SQLiteDataSource
import scala.slick.driver.SQLiteDriver.simple._

object SQLiteDatabaseInitializer {
  def database(dbName: String) = {
    val url = "jdbc:sqlite:%s.db" format dbName
    val ds = new SQLiteDataSource()
    ds.setUrl(url)
    val flyway = new Flyway()
    flyway.setBaselineOnMigrate(true)
    flyway.setDataSource(url, null, null)
    flyway.migrate()
    Database.forDataSource(ds)
  }
}