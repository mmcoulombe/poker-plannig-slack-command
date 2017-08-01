package me.mmcoulombe.ppbot

import java.util.UUID

case class Story(id: UUID, title: String, description: Option[String] = None, point: Int = 0)

object Story {
  def create(title: String, description: Option[String], point: Option[Int]): Story =
    Story(UUID.randomUUID(), title, description, point.getOrElse(0))
}
