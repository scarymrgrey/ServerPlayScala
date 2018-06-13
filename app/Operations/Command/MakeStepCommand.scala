package Operations.Command

import CQRS.Base.CommandBase
import Operations.Entity.{Game, User}
import com.mongodb.casbah.Imports.MongoDBObject
import play.api.libs.json.Json

final case class NotFoundException() extends Exception

object MakeStepCommand {
  implicit val fmt = Json.format[MakeStepCommand]
}

final case class MakeStepCommand(step: Array[Int]) extends CommandBase {
  var currentUser: User = _
  var gameId: String = _

  def getUser(login: String): Option[User] = {
    Repository.GetSome[User](MongoDBObject("login" -> login)).headOption
  }

  override def Execute(): Unit = {
    require(step.nonEmpty && step.length == 2)
    Repository.GetById[Game](gameId).fold(throw NotFoundException()) { game =>
      require(game.players.contains(currentUser.login))
      val gamerIndex = game.players.indexOf(currentUser.login) + 1
      require(game.field(step(0))(step(1)) == 0)
      game.field(step(0))(step(1)) = gamerIndex
      Repository.Modify[Game](game)
      Result = game
    }
  }
}
