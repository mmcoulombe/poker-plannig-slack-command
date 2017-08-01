package me.mmcoulombe.ppbot.route

import akka.http.scaladsl.model.Multipart.FormData
import akka.http.scaladsl.model.HttpEntity
import akka.http.scaladsl.server.{Directives, Route}
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport._
import me.mmcoulombe.ppbot.model.Challenge

/**
  * Created by mmcoulombe on 22/07/17.
  */
class EventRoute extends Directives {
  final val route: Route = {
    pathPrefix("event") {
      pathEndOrSingleSlash {
        post {
          entity(as[Challenge]) { res =>
            complete {
              val m : Map[String, HttpEntity.Strict] = Map("challenge" -> res.challenge)
              FormData(m)
            }
          }
        }
      }
    }
  }
}
