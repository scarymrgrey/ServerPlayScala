package controllers

import CQRS.Base.Dispatcher
import Operations.Command.{CreateNewGameCommand, MakeStepCommand, NotFoundException}
import Operations.Entity.Game
import Operations.Query.{GetEntityById, GetGamesByFilterQuery}
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

  def getGamesByFilter(limit: Int,offset : Int): Action[AnyContent] = Action { implicit request =>
    Try(Dispatcher.Query(GetGamesByFilterQuery(limit,offset))) match {
      case Success(game) => HttpOk(game)
      case Failure(_) => BadRequest
    }
  }

  def newGame(): Action[JsValue] = Authenticated(parse.json) { implicit request =>
    request.body.validate[CreateNewGameCommand].fold(
      _ => BadRequest("Validation error"),
      command => {
        command.currentUser = request.user
        Try(Dispatcher.Push(command)) match {
          case Success(_) => HttpOk(command.Result)
          case Failure(e: IllegalArgumentException) => BadRequest(e.getMessage)
        }
      }
    )
  }

  def makeStep(id : String): Action[JsValue] = Authenticated(parse.json) { implicit request =>
    request.body.validate[MakeStepCommand].fold(
      _ => BadRequest("Validation error"),
      command => {
        command.currentUser = request.user
        command.gameId = id
        Try(Dispatcher.Push(command)) match {
          case Success(_) => HttpOk(command.Result)
          case Failure(e: IllegalArgumentException) => BadRequest(e.getMessage)
          case Failure(_: NotFoundException) => NotFound
        }
      }
    )
  }
}
