package persistence.auth

import org.joda.time.DateTime
import persistence.{DBComponent, Dal, Profile}

import scala.slick.driver.JdbcDriver

case class UserDao(providerId: String,
                   userId: String,
                   firstName: Option[String] = None,
                   lastName: Option[String] = None,
                   fullName: Option[String] = None,
                   email: Option[String] = None,
                   avatarUrl: Option[String] = None,
                   hasher: Option[String],
                   password: Option[String],
                   salt: Option[String],
                   id: Option[Int] = None)

case class TokenDao(uuid: String, email: String, creationTime: DateTime, expirationTime: DateTime, isSignUp: Boolean, id: Option[Int] = None)

class AuthDbComponent extends DBComponent {
  this: Profile =>

  import driver.simple._
  import JodaSupport._

  class Users(tag: Tag) extends Table[UserDao](tag, "USERS") {
    def id = column[Int]("ID", O.PrimaryKey, O.AutoInc, O.NotNull)
    def providerId = column[String]("PROVIDER_ID")
    def userId = column[String]("USER_ID")
    def firstName = column[String]("FIRST_NAME", O.Nullable)
    def lastName = column[String]("LAST_NAME", O.Nullable)
    def fullName = column[String]("FULL_NAME", O.Nullable)
    def email = column[String]("EMAIL", O.Nullable)
    def avatarUrl = column[String]("AVATAR_URL", O.Nullable)
    def hasher = column[String]("HASHER", O.Nullable)
    def password = column[String]("PASSWORD", O.Nullable)
    def salt = column[String]("SALT", O.Nullable)
    def * = {
      val tuple = (providerId, userId, firstName.?, lastName.?, fullName.?, email.?, avatarUrl.?, hasher.?, password.?, salt.?, id.?)
      tuple <>(UserDao.tupled, UserDao.unapply)
    }
  }

  object users extends TableQuery(new Users(_))

  class Tokens(tag: Tag) extends Table[TokenDao](tag, "TOKENS") {
    def id = column[Int]("ID", O.PrimaryKey, O.AutoInc, O.NotNull)
    def uuid = column[String]("UUID")
    def email = column[String]("EMAIL")
    def creationTime = column[DateTime]("CREATION_TIME")
    def expirationTime = column[DateTime]("EXPIRATION_TIME")
    def isSignUp = column[Boolean]("IS_SIGN_UP", O.Nullable)
    def * = (uuid, email, creationTime, expirationTime, isSignUp, id.?) <> (TokenDao.tupled, TokenDao.unapply)
  }

  object tokens extends TableQuery(new Tokens(_))

  val tables = Seq(users, tokens)
}

class AuthDal(override val driver: JdbcDriver) extends AuthDbComponent
  with Dal with Profile {

  import driver.simple._
  override def createStatements: Seq[String] = tables.flatMap(_.ddl.createStatements)
  override def create(implicit session: driver.simple.Session): Unit = tables.foreach(_.ddl.create)
}
