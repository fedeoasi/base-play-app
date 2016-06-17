package controllers

import javax.inject._

import securesocial.MyRuntimeEnvironment
import securesocial.core.SecureSocial

@Singleton
class HomeController @Inject() (override implicit val env: MyRuntimeEnvironment)
  extends SecureSocial {

  def index = SecuredAction {
    Ok(views.html.index("Your new application is ready."))
  }
}
