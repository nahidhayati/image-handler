package lambdas.scheduleTasks

import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}
import lambdas.config
import lambdas.process.ImageProcess
import lambdas.utils.DateUtils
import java.util.logging.Logger
import scala.util.{Failure, Success}

class AddImageHandler extends RequestHandler[Unit, Unit] {

  private val logger: Logger =  Logger.getLogger(this.getClass().getName())

  override def handleRequest(input: Unit, context: Context): Unit = {

    val startDate: String = config.getString("startDate")
    val objectsCount = DateUtils.getDaysFrom(startDate)

    val bucketName: String = sys.env.get("bucketName").getOrElse("")
    val key = s"${objectsCount + 1}.jpeg"

    logger.info("key: " + key)

    ImageProcess.putImage(bucketName, key) match {
      case Success(_) => ()
      case Failure(exception) => logger.severe("Exception: " + exception)
    }
  }

}
