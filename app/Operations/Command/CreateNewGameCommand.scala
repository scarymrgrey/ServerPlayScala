package Operations.Command

import CQRS.Base.CommandBase
import Operations.Entity.{Game, User}
import com.mongodb.casbah.Imports.MongoDBObject
import play.api.libs.json.Json

object CreateNewGameCommand {
  implicit val fmt = Json.format[CreateNewGameCommand]
}

final case class CreateNewGameCommand(
                                       opponent: String,
                                       size: Array[Int],
                                       first_step_by: String,
                                       crosses_length_to_win: Int
                                     ) extends CommandBase {
  var currentUser : User = _
  def getUser(login: String): Option[User] = {
    Repository.GetSome[User](MongoDBObject("login" -> login)).headOption
  }

  override def Execute(): Unit = {
    require(getUser(opponent) nonEmpty,"opponent not exist")
    require(opponent != currentUser.login,"U really want to create a match against your self? tut tebe ne tam, siriuzly :P")
    val firstStep = getUser(first_step_by)
    require(firstStep nonEmpty ,"first_step_by not exist")
    require(List(currentUser.login,opponent) contains firstStep.head.login)

    val game = Game(size = size,
      next_step = first_step_by,
      crosses_length_to_win = crosses_length_to_win,
      players = Array(opponent, currentUser.login),
      field =  Array.ofDim[Int](size(0), size(1))
    )
    Repository.Save[Game](game)
    Result = game
  }
}
