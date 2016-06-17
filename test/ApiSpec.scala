import org.scalatest.{FunSpec, Matchers}
import org.scalatestplus.play.OneServerPerSuite
import play.api.libs.ws.{WS, WSRequest, WSResponse}
import play.api.test.{DefaultAwaitTimeout, FutureAwaits}

class ApiSpec extends FunSpec with Matchers with OneServerPerSuite with FutureAwaits with DefaultAwaitTimeout {
  val myPublicAddress = s"localhost:$port"

  describe("API") {
    describe("/status") {
      val url = buildUrlString("/status")
      it("returns a green status") {
        withResponse(url) { response =>
          response.status shouldBe 200
          response.body shouldBe "GREEN"
        }
      }
    }
  }

  private def buildUrlString(path: String) = s"http://$myPublicAddress$path"

  private def withResponse(url: String,
                           transformRequest: WSRequest => WSRequest = identity)
                          (test: WSResponse => Unit): Unit = {
    val wsRequest = transformRequest(WS.url(url))
    val response = await(wsRequest.get())

    test(response)
  }

}