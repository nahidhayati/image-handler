package lambdas.endpoints

import com.amazonaws.services.lambda.runtime.events.{APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent}
import com.amazonaws.services.lambda.runtime.{Context, LambdaLogger, RequestHandler}
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.util.IOUtils
import lambdas.process.ImageProcess
import lambdas.utils.{DateUtils, TryBase64ToResponseComponent}
import java.util.Base64
import java.util.logging.Logger

class GetRandomImageHandler
  extends RequestHandler[Unit, APIGatewayProxyResponseEvent]
    with TryBase64ToResponseComponent {

  private val logger: Logger =  Logger.getLogger(this.getClass().getName())

  override def handleRequest(input: Unit, context: Context): APIGatewayProxyResponseEvent = {

    val bucketName: String = sys.env.get("bucketName").getOrElse("")
    val objectsCount = DateUtils.getDaysFrom("2023-07-01")
    val rnd = util.Random.between(1, objectsCount)
    val key = s"$rnd.jpeg"

    logger.info(s"Objects count: $objectsCount --- Random number: $rnd")

    ImageProcess.getImage(bucketName, key)
  }
}
