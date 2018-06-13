package Operations.Command

import CQRS.Base.CommandBase
import Operations.Entity.{Game, User}
import play.api.libs.json.Json

final case class NotFoundException() extends Exception

object MakeStepCommand {
  implicit val fmt = Json.format[MakeStepCommand]
}

final case class MakeStepCommand(step: Array[Int]) extends CommandBase {
  var currentUser: User = _
  var gameId: String = _
  val toRight = Vector(1, 0)
  val toRightAndUp = Vector(1, 1)
  val toUp = Vector(0, 1)

  case class Vector(x: Int, y: Int) {
    def +(other: Vector) = Vector(x + other.x, y + other.y)

    def -(other: Vector) = Vector(x - other.x, y - other.y)
  }

  def get(field: Array[Array[Int]], x: Int, y: Int, player: Int): Boolean = {
    val maxY = field(0).length
    val maxX = field.length

    x >= 0 &&
      x < maxX &&
      y >= 0 &&
      y < maxY &&
      field(x)(y) == player
  }

  def maxLine(field: Array[Array[Int]], step: Vector, direction: Vector, player: Int): Int = {
    var d = step
    var length = 0
    do {
      length += 1
      d += direction
    } while (get(field, d.x, d.y, player))

    d = step - direction
    while (get(field, d.x, d.y, player)) {
      length += 1
      d -= direction
    }
    length
  }

  override def Execute(): Unit = {
    require(step.nonEmpty && step.length == 2)
    Repository.GetById[Game](gameId).fold(throw NotFoundException()) { game =>
      require(game.players.contains(currentUser.login))
      require(game.next_step == currentUser.login)
      require((0 until game.size(0)).contains(step(0)))
      require((0 until game.size(1)).contains(step(1)))
      require(game.field(step(0))(step(1)) == 0)
      require(!game.finished)
      val gamerIndex = game.players.indexOf(currentUser.login) + 1
      game.field(step(0))(step(1)) = gamerIndex
      val maxL = Array(toRight, toRightAndUp, toUp)
        .map(r => maxLine(game.field, Vector(step(0), step(1)), r, gamerIndex))
        .max
      if (maxL == game.crosses_length_to_win) {
        game.won = currentUser.login
        game.finished = true
      } else {
        game.next_step = (game.players filterNot (_ == currentUser.login)) (0)
      }
      game.steps += 1
      Repository.Modify[Game](game)
      Result = game
    }
  }
}
