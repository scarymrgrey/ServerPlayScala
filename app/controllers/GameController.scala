package controllers

import CQRS.Base.Dispatcher
import Operations.Command.CreateNewGameCommand
import Operations.Entity.Game
import Operations.Query.{GetEntityById, GetGameByIdQuery}
import javax.inject._
import play.api.libs.json.JsValue
import play.api.mvc._

import scala.util.{Failure, Success, Try}

@Singleton
class GameController @Inject()(cc: ControllerComponents) extends BaseController(cc) {

  def getGame(id: String): Action[AnyContent] = Action { implicit request =>
    Dispatcher.Query(GetEntityById[Game](id)) match {
      case Some(game) => HttpOk(game)
      case None => NotFound
    }
  }

  def newGame(): Action[JsValue] = Authenticated(parse.json) { implicit request =>
    request.body.validate[CreateNewGameCommand].fold(
      _ => BadRequest("Validation error"),
      command => {
        command.currentUser = request.user
        Try(Dispatcher.Push(command)) match {
          case Success(_) => Ok("Created")
          case Failure(e: IllegalArgumentException) => BadRequest(e.getMessage)
        }
      }
    )
  }
}
