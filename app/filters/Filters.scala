package filters

import javax.inject._

import play.api.http.HttpFilters

@Singleton
class Filters @Inject() (loggingFilter: LoggingFilter) extends HttpFilters {
  override val filters = Seq(loggingFilter)
}
