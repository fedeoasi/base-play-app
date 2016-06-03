package persistence

import java.io.File
import java.util.UUID

import org.scalatest.{BeforeAndAfterAll, FunSpec, Matchers}
import persistence.auth.AuthPersistenceServiceImpl

class DatabaseMigrationSpec extends FunSpec with Matchers with BeforeAndAfterAll {
  private val dbName = s"testDb${UUID.randomUUID()}"
  private val db = SQLiteDatabaseInitializer.database(dbName)

  describe("Database Migration Step") {
    it("supports the auth model") {
      val ps = new AuthPersistenceServiceImpl(db)
      ps.findUserByEmail("hello@gmail.com") shouldBe None
    }
  }

  override protected def afterAll(): Unit = {
    new File(s"$dbName.db").delete()
  }
}
