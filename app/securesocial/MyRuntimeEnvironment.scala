package securesocial

import com.google.inject.{Inject, Singleton}
import model.User
import securesocial.core.providers.UsernamePasswordProvider
import securesocial.core.services.UserService
import securesocial.core.{IdentityProvider, RuntimeEnvironment}
import services.InMemoryUserService

import scala.collection.immutable.ListMap

@Singleton
class MyRuntimeEnvironment @Inject() () extends RuntimeEnvironment.Default {
  override type U = User
  override implicit val executionContext = play.api.libs.concurrent.Execution.defaultContext
  override lazy val userService: UserService[User] = new InMemoryUserService
  override lazy val eventListeners = List(new SecureSocialEventListener())
  override lazy val providers: ListMap[String, IdentityProvider] = ListMap(
    include(new UsernamePasswordProvider[User](userService, avatarService, viewTemplates, passwordHashers))
  )
}