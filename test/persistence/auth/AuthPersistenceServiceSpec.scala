package persistence.auth

import model.User
import org.joda.time.DateTime
import org.scalatest.{FunSpec, Matchers}
import persistence.auth.AuthSpecHelper._
import securesocial.core.providers.MailToken
import securesocial.core.{AuthenticationMethod, PasswordInfo}

class AuthPersistenceServiceSpec extends FunSpec with Matchers {
  describe("User Persistence Service") {
    describe("Users") {
      val somePasswordInfo = Some(PasswordInfo("bcrypt", "hashedpwd", None))

      it("should not find a non existent user") {
        val ps = buildPersistenceService()
        ps.findUserByUserId("uid") shouldBe None
      }

      it("should add a new user and find it by user id") {
        val ps = buildPersistenceService()
        val profile = buildProfile("pid", "uid", AuthenticationMethod.UserPassword, Some("First"), Some("Last"),
          Some("First Last"), Some("abc@gmail.com"), Some("avatar"), None, None, somePasswordInfo)
        ps.saveUser(profile)
        ps.findUserByUserId("uid") shouldBe Some(profile)
      }

      it("does not recreate the same user") {
        val ps = buildPersistenceService()
        val profile = buildProfile("pid", "uid", AuthenticationMethod.UserPassword, Some("First"), Some("Last"),
          Some("First Last"), Some("abc@gmail.com"), Some("avatar"), None, None, somePasswordInfo)
        ps.userCount shouldBe 0
        ps.saveUser(profile)
        ps.userCount shouldBe 1
        ps.saveUser(profile)
        ps.userCount shouldBe 1
      }

      it("should add a new user and find it by email") {
        val ps = buildPersistenceService()
        val profile = buildProfile("pid", "uid", AuthenticationMethod.UserPassword, email = Some("abc@gmail.com"))
        ps.saveUser(profile)
        ps.findUserByUserId("uid") shouldBe Some(profile)
      }

      it("should find the password info for an existing user") {
        val ps = buildPersistenceService()
        val profile = buildProfile("pid", "uid", AuthenticationMethod.UserPassword, Some("First"), Some("Last"),
          Some("First Last"), Some("abc@gmail.com"), Some("avatar"), None, None, somePasswordInfo)
        ps.saveUser(profile)
        val user = User(profile, List(profile), 1)
        ps.passwordInfoFor(user.main) shouldBe somePasswordInfo
      }
    }

    describe("Tokens") {
      val uuid = "asdfasdf"
      val token = MailToken(uuid, "abc@gmail.com", DateTime.now(), DateTime.now(), isSignUp = true)

      it("should not find a non existent token") {
        val ps = buildPersistenceService()
        ps.findToken("asasdjfhkasdf") shouldBe None
      }

      it("should add and find a token") {
        val ps = buildPersistenceService()
        ps.saveToken(token)
        ps.findToken(uuid) shouldBe Some(token)
      }

      it("should not delete a non existent token") {
        val ps = buildPersistenceService()
        ps.deleteToken("aksdfaasdf") shouldBe None
      }

      it("should add and delete token") {
        val ps = buildPersistenceService()
        ps.saveToken(token)
        ps.deleteToken(uuid) shouldBe Some(token)
      }
    }
  }
  
  private def buildPersistenceService(): BaseAuthPersistenceService = {
    new TestAuthPersistenceService()
  }
}
