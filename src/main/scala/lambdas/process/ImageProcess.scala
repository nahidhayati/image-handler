package lambdas.process

import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.util.IOUtils
import java.util.Base64
import scala.util.Try

object ImageProcess {

  def getImage(bucketName: String, key: String): Try[String] = Try {
    val s3Client = AmazonS3ClientBuilder.defaultClient()
    val s3Object = s3Client.getObject(bucketName, key)
    val imageBytes = IOUtils.toByteArray(s3Object.getObjectContent())
    Base64.getEncoder.encodeToString(imageBytes)
  }

}
