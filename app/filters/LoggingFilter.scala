package filters

import akka.stream.Materializer
import com.google.inject.Inject
import org.slf4j.LoggerFactory
import play.api.mvc.{Filter, RequestHeader, Result}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}

class LoggingFilter @Inject()(implicit val mat: Materializer,
                              ec: ExecutionContext) extends Filter {
  private val logger = LoggerFactory.getLogger("request")
  private val loggingBlackList = Seq("/assets", "/status")

  def apply(nextFilter: (RequestHeader) => Future[Result])
           (request: RequestHeader): Future[Result] = {
    val startTime = System.currentTimeMillis
    nextFilter(request).map { result =>
      val endTime = System.currentTimeMillis
      val requestTime = endTime - startTime
      if (!loggingBlackList.exists(request.path.contains)) {
        val status = result.header.status
        logger.info(s"$request $status ${requestTime}ms")
      }
      result
    }
  }
}