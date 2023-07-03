package lambdas.utils

import com.amazonaws.services.lambda.runtime.LambdaLogger
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import scala.language.implicitConversions

import scala.util.{Failure, Success, Try}

trait TryBase64ToResponseComponent {

  implicit def TryBase64ToResponse(result: Try[String])(implicit logger: LambdaLogger): APIGatewayProxyResponseEvent = {
    result match {
      case Success(base64Image) =>
        val response = new APIGatewayProxyResponseEvent()
        response.setStatusCode(200)
        response.setBody(base64Image)
        response.setIsBase64Encoded(true)
        response
      case Failure(exception) =>
        logger.log("Exception: " + exception.getMessage)
        val response = new APIGatewayProxyResponseEvent()
        response.setStatusCode(500)
        response.setBody("Error occurred while processing the image")
        response
    }
  }

}
