package securesocial

import com.google.inject.{Inject, Singleton}
import model.User
import persistence.auth.{AuthPersistenceService, UserServiceImpl}
import securesocial.core.providers.UsernamePasswordProvider
import securesocial.core.services.UserService
import securesocial.core.{IdentityProvider, RuntimeEnvironment}

import scala.collection.immutable.ListMap

@Singleton
class MyRuntimeEnvironment @Inject() (authService: AuthPersistenceService) extends RuntimeEnvironment.Default {
  override type U = User
  override implicit val executionContext = play.api.libs.concurrent.Execution.defaultContext
  override lazy val userService: UserService[User] = new UserServiceImpl(authService)
  override lazy val providers: ListMap[String, IdentityProvider] = ListMap(
    include(new UsernamePasswordProvider[User](userService, avatarService, viewTemplates, passwordHashers))
  )
}