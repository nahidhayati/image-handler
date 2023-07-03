package lambdas.endpoints

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}

class PingPongHandler extends RequestHandler[Unit, APIGatewayProxyResponseEvent]{

  override def handleRequest(input: Unit, context: Context): APIGatewayProxyResponseEvent = {
    val response = new APIGatewayProxyResponseEvent()
    response.setStatusCode(200)
    response.setBody("pong")
    response
  }

}
