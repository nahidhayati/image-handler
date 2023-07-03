package lambdas.utils

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import java.util.logging.Logger
import scala.language.implicitConversions
import scala.util.{Failure, Success, Try}

trait TryBase64ToResponseComponent {

  private val logger: Logger =  Logger.getLogger(this.getClass().getName())

  implicit def TryBase64ToResponse(result: Try[String]): APIGatewayProxyResponseEvent = {
    result match {
      case Success(base64Image) =>
        val response = new APIGatewayProxyResponseEvent()
        response.setStatusCode(200)
        response.setBody(base64Image)
        response.setIsBase64Encoded(true)
        response
      case Failure(exception) =>
        logger.severe("Exception: " + exception.getMessage)
        val response = new APIGatewayProxyResponseEvent()
        response.setStatusCode(500)
        response.setBody("Error occurred while processing the image")
        response
    }
  }

}
