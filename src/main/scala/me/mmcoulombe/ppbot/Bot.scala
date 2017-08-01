package me.mmcoulombe.ppbot

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import me.mmcoulombe.ppbot.route.{EventRoute, PPCommandRoute}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object Bot {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("PPBot")
    implicit val materializer = ActorMaterializer()

    val config = ConfigFactory.load()
    val interface = config.getString("http.interface")
    val port = config.getInt("http.port")

    val events = new PPCommandRoute

    Http()
      .bindAndHandle(events.route, interface, port)
      .onComplete {
        case Success(binding) =>
          val address = binding.localAddress
          println(s"Server is listening on ${address.getHostName}:${address.getPort}")
        case Failure(e) =>
          println(s"Error launching akka-http: ${e.getMessage}")
          system.terminate()
      }
  }
}
