package me.mmcoulombe.ppbot.model

import play.api.libs.json.{Json, Reads}

/**
  * Created by mmcoulombe on 22/07/17.
  */
case class Challenge(token: String, challenge: String, `type`: String)

object Challenge {
  implicit val jsonRead: Reads[Challenge] = Json.reads[Challenge]
}
