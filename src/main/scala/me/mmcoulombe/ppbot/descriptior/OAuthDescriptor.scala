package me.mmcoulombe.ppbot.descriptior

import akka.http.scaladsl.model.FormData

/**
  * Created by mmcoulombe on 22/07/17.
  */
trait OAuthDescriptor {
  def authorizeBot(): FormData
}
