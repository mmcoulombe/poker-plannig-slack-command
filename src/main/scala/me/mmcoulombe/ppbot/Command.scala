package me.mmcoulombe.ppbot

import scala.concurrent.Future
import scala.util.Try

trait ExecutableCommand[T] {

  def validateArgs(): Either[String, T]

  def ready(value: T): Future[String]

  final def execute(): Future[String] = {
    val command = validateArgs()
      command.fold(s => Future.successful(s),
      ready
    )
  }
}

class StringCommand(name: String, args: Iterable[String]) {
  def toExecutableCommand: ExecutableCommand[_] = {
    name.toLowerCase match {
      case SupportedCommands.ADD_STORY => new AddStoryCommand(args)
      case _ => new BadCommandName(name.toLowerCase)
    }
  }
}

class AddStoryCommand(args: Iterable[String]) extends ExecutableCommand[Story] {

  override def validateArgs(): Either[String, Story] = {
   args.toList.length match {
      case 0 => Left("Aguments are missing")
      case 1 => Right(Story create (args.head, None, None))
      case 2 => Right(Story create (args.head, args(1), None))
      case 3 =>
        Try(args.head.toInt)
          .map(point => Story create (args.head, args(1), Some(point)))
          .toEither.left.map(_ => "The point value entered is not a number")
    }
  }

  def ready(story: Story): Future[String] = {
    Planning.addStory(story)
    Future.successful("aaa")
  }
}

object DescriptionCommand extends ExecutableCommand[Unit] {
  override def validateArgs(): Either[String, Unit] = Right(Unit)

  override def ready(value: Unit): Future[String] = {
    val hasDesc: Option[String] => Future[String] =
      _.fold(Future.successful("There is no description for this story"))(Future.successful)

    Planning.currentStory
      .map(story => story.description)
      .fold(Future.successful("There is no active story, did you start the planning ?"))(hasDesc)
  }
}

class BadCommandName(name: String) extends ExecutableCommand[Unit] {

  override def validateArgs(): Either[String, Unit] = Right(Unit)

  override def ready(value: Unit): Future[String] =
    Future.successful(s"Command $name does not exist")

}

object SupportedCommands {
  final val ADD_STORY: String    = "add"
  final val DESCRIPTION: String  = "desc"
  final val NEXT_STORY: String   = "next"
  final val PREPARE: String      = "prepare"
  final val PREV_STORY: String   = "previous"
  final val REMOVE_STORY: String = "remove"
  final val START: String        = "start"
  final val VOTE: String         = "vote"

}

object Command {
  def parse(str: String): StringCommand =
    str.split(" ").toList match {
      case name :: args => new StringCommand(name, args)
    }
}
