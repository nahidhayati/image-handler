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
    val service = OpenAIServiceFactory(apiKey = apiKey)

    logger.info(s"apiKey: " + apiKey)

    val prompt = "A cat that is playing with a little girl, digital art."
    val settings = CreateImageSettings(
      n = Some(1),
      size = Some(ImageSizeType.Large),
      response_format = Some(ImageResponseFormatType.b64_json)
    )

    service.createImage(prompt, settings).map(_.data.head.values.head)
  }

}
