package lambdas.client

import io.cequence.openaiscala.domain.settings.{CreateImageSettings, ImageResponseFormatType, ImageSizeType}
import io.cequence.openaiscala.service.OpenAIServiceFactory
import lambdas._
import scala.concurrent.Future
import java.util.logging.Logger

object OpenAi {

  private val logger: Logger =  Logger.getLogger(this.getClass().getName())

  def getImage(): Future[String] = {

    val apiKey: String = config.getString("openAiApiKey")

    logger.info(s"apiKey: " + apiKey)

    val service = OpenAIServiceFactory(apiKey = apiKey)

    val setting = CreateImageSettings(
      n = Some(1),
      size = Some(ImageSizeType.Large),
      response_format = Some(ImageResponseFormatType.b64_json)
    )

    service.createImage("a cute cat", setting).map {
      imgInfo =>
        logger.info("------after create Image: " + imgInfo.data.headOption.flatMap(_.headOption).getOrElse("-100"))
        imgInfo.data.head.values.head
    }
  }

}
