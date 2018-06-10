package controllers

import CQRS.Base.Dispatcher
import Operations.Command.{CreateNewGameCommand, CreateNewUserCommand}
import javax.inject._
import play.api.libs.json.JsValue
import play.api.mvc._

import scala.util.{Failure, Success, Try}

@Singleton
class GameController @Inject()(cc: ControllerComponents) extends BaseController(cc) {

    def newGame(): Action[JsValue] = Authenticated { implicit request =>
      request.body.validate[CreateNewGameCommand].fold(
        errors => BadRequest("Validation error"),
        command => {
          command.currentUser = request.user
          Try(Dispatcher.Push(command)) match {
            case Success(_) => Ok("Created")
            case Failure(e : IllegalArgumentException) => BadRequest(e.getMessage)
          }
        }
      )
    }
}
