package lambdas.endpoints

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import com.amazonaws.services.lambda.runtime.{Context, LambdaLogger, RequestHandler}
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.util.IOUtils
import java.util.Base64
import lambdas.utils.DateUtils

class GetDailyImageHandler extends RequestHandler[Unit, APIGatewayProxyResponseEvent] {

  override def handleRequest(input: Unit, context: Context): APIGatewayProxyResponseEvent = {
    val logger: LambdaLogger = context.getLogger()

    val bucketName: String = sys.env.get("bucketName").getOrElse("")
    val objectsCount = DateUtils.getDaysFrom("2023-07-01")
    val key = s"$objectsCount.jpeg"
    val s3Client = AmazonS3ClientBuilder.defaultClient()

    try {
      val s3Object = s3Client.getObject(bucketName, key)
      val imageBytes = IOUtils.toByteArray(s3Object.getObjectContent())
      val base64Image = Base64.getEncoder.encodeToString(imageBytes)

      val response = new APIGatewayProxyResponseEvent()
      response.setStatusCode(200)
      response.setBody(base64Image)
      response.setIsBase64Encoded(true)
      response
    } catch {
      case e: Exception =>
        logger.log("Exception: " + e.getMessage)
        val response = new APIGatewayProxyResponseEvent()
        response.setStatusCode(500)
        response.setBody("Error occurred while processing the image")
        response
    }
  }

}
