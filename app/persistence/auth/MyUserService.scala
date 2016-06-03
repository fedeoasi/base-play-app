package persistence.auth

import model.User
import play.api.Logger
import securesocial.core.providers.MailToken
import securesocial.core.services.{SaveMode, UserService}
import securesocial.core.{BasicProfile, PasswordInfo}

import scala.concurrent.Future

class MyUserService(aps: AuthPersistenceService) extends UserService[User] {
  val logger = Logger(this.getClass)

  override def find(providerId: String, userId: String): Future[Option[BasicProfile]] = Future.successful {
    aps.findUserByUserId(userId)
  }

  override def findByEmailAndProvider(email: String, providerId: String): Future[Option[BasicProfile]] = Future.successful {
    aps.findUserByUserId(email)
  }

  override def deleteToken(uuid: String): Future[Option[MailToken]] = Future.successful {
    aps.deleteToken(uuid)
  }

  override def link(current: User, to: BasicProfile): Future[User] = {
    throw new NotImplementedError("Linking profiles is not yet supported")
  }

  override def passwordInfoFor(user: User): Future[Option[PasswordInfo]] = Future.successful {
    aps.passwordInfoFor(user.main)
  }

  override def save(profile: BasicProfile, mode: SaveMode): Future[User] = Future.successful {
    val savedDao = aps.saveUser(profile)
    User(profile, List(profile), savedDao.id.get)
  }

  override def findToken(token: String): Future[Option[MailToken]] = Future.successful {
    aps.findToken(token)
  }

  override def deleteExpiredTokens(): Unit = {
    throw new NotImplementedError("Linking profiles is not yet supported")
  }

  override def updatePasswordInfo(user: User, info: PasswordInfo): Future[Option[BasicProfile]] = Future.successful {
    aps.updatePasswordInfo(user.main, info)
  }

  override def saveToken(token: MailToken): Future[MailToken] = Future.successful {
    aps.saveToken(token)
  }
}
