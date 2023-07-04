package lambdas.endpoints

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}
import lambdas.config
import lambdas.process.ImageProcess
import lambdas.utils.{DateUtils, TryBase64ToResponseComponent}
import java.util.logging.Logger

class GetRandomImageHandler
  extends RequestHandler[Unit, APIGatewayProxyResponseEvent]
    with TryBase64ToResponseComponent {

  private val logger: Logger =  Logger.getLogger(this.getClass().getName())

  override def handleRequest(input: Unit, context: Context): APIGatewayProxyResponseEvent = {

    val startDate: String = config.getString("startDate")
    val objectsCount = DateUtils.getDaysFrom(startDate)
    val rnd = util.Random.between(1, objectsCount)

    val bucketName: String = sys.env.get("bucketName").getOrElse("")
    val key = s"$rnd.jpeg"

    logger.info(s"Objects count: $objectsCount --- Random number: $rnd")

    ImageProcess.getImage(bucketName, key)
  }
}
