package me.mmcoulombe.ppbot.route

import akka.http.scaladsl.server.{Directives, Route}
import com.typesafe.config.ConfigFactory
import me.mmcoulombe.ppbot.Command

import scala.concurrent.Future

trait Authorization {
  def validateToken(token: String)(fn: => Future[String]): Future[String] = {
    val appToken = ConfigFactory.load().getString("slack.token")
    if (token == appToken) {
      fn()
    } else {
      Future.successful("")
    }
  }
}

/**
  * Created by mmcoulombe on 22/07/17.
  */
class PPCommandRoute extends Directives with Authorization{
  val route: Route = {
    pathPrefix("planning") {
      pathEndOrSingleSlash {
        post {
          formField('token, 'text) { (token, text) =>
            complete(
              validateToken(token) {
                Command
                  .parse(text)
                  .toExecutableCommand()
                  .execute()
                Future.successful(s"Hello $text")
              }
            )
          }
        }
      }
    }
  }
}
