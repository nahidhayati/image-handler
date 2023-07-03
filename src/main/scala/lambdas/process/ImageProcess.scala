package lambdas.process

import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.{ObjectMetadata, PutObjectResult}
import com.amazonaws.util.IOUtils
import lambdas.client.OpenAi
import java.io.ByteArrayInputStream
import java.util.Base64
import scala.concurrent.Await
import scala.concurrent.duration.{Duration, MILLISECONDS}
import scala.util.Try

object ImageProcess {

  def getImage(bucketName: String, key: String): Try[String] = Try {
    val s3Client = AmazonS3ClientBuilder.defaultClient()
    val s3Object = s3Client.getObject(bucketName, key)
    val imageBytes = IOUtils.toByteArray(s3Object.getObjectContent())
    Base64.getEncoder.encodeToString(imageBytes)
  }

  def putImage(bucketName: String, key: String): Try[PutObjectResult] = Try {

    val base64Image = Await.result(OpenAi.getImage(), Duration(20000, MILLISECONDS))

    val imageBytes = Base64.getDecoder().decode(base64Image)
    val streamImage = new ByteArrayInputStream(imageBytes)

    val metadata: ObjectMetadata = new ObjectMetadata()
    metadata.setContentType("image/jpeg")
    metadata.setContentLength(imageBytes.length)

    val s3Client = AmazonS3ClientBuilder.defaultClient()
    s3Client.putObject(bucketName, key, streamImage, metadata)
  }

}
