package me.mmcoulombe.ppbot

/**
  * What do we do during the poker planning
  * 1. We choose a story from the backlog
  *   - We need to have the list of all story in the backlog
  *   - (JIRA integration ??)
  *
  * 2. We discuss it and vote using number (use a Fibonacci(ish) number list)
  *   - We need to have all the participant to be able to vote
  *
  * 3. We display the vote and talk
  * 4. We decide the number of point for the story
  * 5. When done, we go to the next story in the backlog
  */
object Planning {
  private var stories: Iterable[Story] = List.empty
  var currentStory: Option[Story] = None

  def addStory(story: Story): Unit =
    stories = stories ++ List(story)

  def removeStory(story: Story): Unit =
    stories = stories.filter(_.id != story.id)
}
