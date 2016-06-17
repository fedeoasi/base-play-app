package controllers

import javax.inject.{Inject, Singleton}

import play.api.mvc.{Action, Controller}

@Singleton
class StatusController @Inject() extends Controller {
  def status() = Action {
    Ok("GREEN")
  }
}
