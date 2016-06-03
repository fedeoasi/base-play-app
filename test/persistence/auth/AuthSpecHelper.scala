package persistence.auth

import model.User
import securesocial.core.{AuthenticationMethod, BasicProfile}

object AuthSpecHelper {
  val baseProfile = buildProfile("pid", "uid", AuthenticationMethod.UserPassword)
  val user = User(baseProfile, List(baseProfile), 1)

  def buildProfile(providerId: String,
                           userId: String,
                           authMethod : securesocial.core.AuthenticationMethod,
                           firstName: scala.Option[String] = None,
                           lastName: scala.Option[String] = None,
                           fullName: scala.Option[String] = None,
                           email : scala.Option[String] = None,
                           avatarUrl : scala.Option[String] = None,
                           oAuth1Info : scala.Option[securesocial.core.OAuth1Info] = None,
                           oAuth2Info : scala.Option[securesocial.core.OAuth2Info] = None,
                           passwordInfo : scala.Option[securesocial.core.PasswordInfo] = None): BasicProfile = {
    BasicProfile(providerId, userId, firstName, lastName, fullName, email, avatarUrl, authMethod, oAuth1Info, oAuth2Info, passwordInfo)
  }
}
