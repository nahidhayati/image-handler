
import akka.actor.ActorSystem
import com.typesafe.config.{Config, ConfigFactory}
import scala.concurrent.ExecutionContextExecutor

package object lambdas {
  implicit val actorSystem: ActorSystem = ActorSystem("cats")
  implicit val config: Config = ConfigFactory.load()
  implicit val ec: ExecutionContextExecutor = actorSystem.dispatcher
}
