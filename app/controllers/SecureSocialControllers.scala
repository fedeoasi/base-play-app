package controllers

import com.google.inject.Inject
import securesocial.MyRuntimeEnvironment
import securesocial.controllers.{BaseLoginApi, _}

class CustomLoginController @Inject() (override implicit val env: MyRuntimeEnvironment) extends BaseLoginPage

class CustomProviderController @Inject() (override implicit val env: MyRuntimeEnvironment) extends BaseProviderController

class CustomRegistrationController @Inject() (override implicit val env: MyRuntimeEnvironment) extends BaseRegistration

class CustomPasswordResetController @Inject() (override implicit val env: MyRuntimeEnvironment) extends BasePasswordReset

class CustomPasswordChangeController @Inject() (override implicit val env: MyRuntimeEnvironment) extends BasePasswordChange

class CustomLoginApiController @Inject() (override implicit val env: MyRuntimeEnvironment) extends BaseLoginApi
