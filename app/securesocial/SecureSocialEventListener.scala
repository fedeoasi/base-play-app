package securesocial

import play.api.Logger
import play.api.mvc.{RequestHeader, Session}
import securesocial.core.{Event, EventListener}

class SecureSocialEventListener extends EventListener {
  override def onEvent[U](event: Event[U], request: RequestHeader, session: Session): Option[Session] = {
    Logger.info(event.toString)
    None
  }
}
