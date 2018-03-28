package controllers

import javax.inject._

import securesocial.CustomRuntimeEnvironment
import securesocial.core.SecureSocial

@Singleton
class HomeController @Inject() (override implicit val env: CustomRuntimeEnvironment)
  extends SecureSocial {

  def index = SecuredAction {
    Ok(views.html.index())
  }
}
