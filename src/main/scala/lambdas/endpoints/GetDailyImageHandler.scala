package lambdas.endpoints

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import com.amazonaws.services.lambda.runtime.{Context, LambdaLogger, RequestHandler}
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.util.IOUtils
import lambdas.process.ImageProcess

import java.util.Base64
import lambdas.utils.{DateUtils, TryBase64ToResponseComponent}

import scala.util.{Failure, Success}

class GetDailyImageHandler
  extends RequestHandler[Unit, APIGatewayProxyResponseEvent]
    with TryBase64ToResponseComponent {

  override def handleRequest(input: Unit, context: Context): APIGatewayProxyResponseEvent = {
    implicit val logger: LambdaLogger = context.getLogger()

    val bucketName: String = sys.env.get("bucketName").getOrElse("")
    val objectsCount = DateUtils.getDaysFrom("2023-07-01")
    val key = s"$objectsCount.jpeg"

    logger.log(s"Objects count: $objectsCount")

    ImageProcess.getImage(bucketName, key)
  }

}
