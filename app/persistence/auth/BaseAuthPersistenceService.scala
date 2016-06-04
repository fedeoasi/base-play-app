package persistence.auth

import play.api.Logger
import securesocial.core.providers.MailToken
import securesocial.core.{AuthenticationMethod, BasicProfile, PasswordInfo}

import scala.slick.driver.SQLiteDriver.simple._

trait AuthPersistenceService {
  def findUserByUserId(userId: String): Option[BasicProfile]
  def findUserByEmail(email: String): Option[BasicProfile]
  def saveUser(p: BasicProfile): UserDao
  def saveToken(t: MailToken): MailToken
  def findToken(uuid: String): Option[MailToken]
  def deleteToken(uuid: String): Option[MailToken]
  def passwordInfoFor(p: BasicProfile): Option[PasswordInfo]
  def updatePasswordInfo(p: BasicProfile, info: PasswordInfo): Option[BasicProfile]
}

abstract class BaseAuthPersistenceService extends AuthPersistenceService {
  val database: Database
  val dal: AuthDal

  import dal._
  import driver.simple._

  val logger = Logger.apply(classOf[BaseAuthPersistenceService])

  def findUserByUserId(userId: String): Option[BasicProfile] = {
    database.withSession { implicit s =>
      users.filter(_.userId === userId).list.map(basicProfile).headOption
    }
  }

  def findUserByEmail(email: String): Option[BasicProfile] = {
    database.withSession { implicit s =>
      users.filter(_.email === email).list.map(basicProfile).headOption
    }
  }

  private def basicProfile(d: UserDao): BasicProfile = {
    val passInfo = d.hasher.map { h =>
      PasswordInfo(h, d.password.get, d.salt)
    }
    BasicProfile(d.providerId, d.userId, d.firstName, d.lastName, d.fullName, d.email, d.avatarUrl, AuthenticationMethod.UserPassword, None, None, passInfo)
  }

  def saveUser(p: BasicProfile): UserDao = {
    val newDao = userDao(p, p.passwordInfo)
    database.withSession { implicit s =>
      val query = users.filter(_.email === p.email)
      query.list.headOption match {
        case Some(oldDao) =>
          val updatedDao = newDao.copy(id = oldDao.id)
          query.update(updatedDao)
          updatedDao
        case None =>
          val id = users.returning(users.map(_.id)).insert(newDao)
          newDao.copy(id = Some(id))
      }
    }
  }

  private def userDao(p: BasicProfile, pi: Option[PasswordInfo]): UserDao = {
    val hasher = pi.map(_.hasher)
    val password = pi.map(_.password)
    val salt = pi.flatMap(_.salt)
    UserDao(p.providerId, p.userId, p.firstName, p.lastName, p.fullName, p.email, p.avatarUrl, hasher, password, salt)
  }

  def userCount: Int = {
    database.withSession { implicit s =>
      users.list.size
    }
  }

  def saveToken(t: MailToken): MailToken = {
    database.withSession { implicit s =>
      val dao = TokenDao(t.uuid, t.email, t.creationTime, t.expirationTime, t.isSignUp)
      tokens.insert(dao)
      t
    }
  }

  def findTokenQuery(uuid: String): Query[Tokens, Tokens#TableElementType, Seq] = {
    tokens.filter(_.uuid === uuid)
  }

  def findToken(uuid: String): Option[MailToken] = {
    database.withSession { implicit s =>
      findTokenQuery(uuid).list.headOption.map { d =>
        MailToken(d.uuid, d.email, d.creationTime, d.expirationTime, d.isSignUp)
      }
    }
  }

  def passwordInfoFor(p: BasicProfile): Option[PasswordInfo] = {
    database.withSession { implicit s =>
      users.filter(_.userId === p.userId).list.headOption.flatMap { d =>
        d.hasher.map { h =>
          PasswordInfo(h, d.password.get, d.salt)
        }
      }
    }
  }

  def updatePasswordInfo(p: BasicProfile, info: PasswordInfo): Option[BasicProfile] = {
    val newDao = userDao(p, Some(info))
    database.withSession { implicit s =>
      val query = users.filter(_.email === p.email)
      query.list.headOption.map { oldDao =>
        query.update(newDao.copy(id = oldDao.id))
        basicProfile(newDao)
      }
    }
  }

  private def mailToken(d: TokenDao): MailToken = {
    MailToken(d.uuid, d.email, d.creationTime, d.expirationTime, d.isSignUp)
  }

  def deleteToken(uuid: String): Option[MailToken] = {
    database.withSession { implicit s =>
      val query = findTokenQuery(uuid)
      query.list.headOption.map { t =>
        query.delete
        mailToken(t)
      }
    }
  }
}
