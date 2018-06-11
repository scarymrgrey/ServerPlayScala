package controllers

import CQRS.Base.Dispatcher
import Operations.Command.CreateNewUserCommand
import Operations.Query.SignInQuery
import javax.inject._
import play.api.libs.json.JsValue
import play.api.mvc._

import scala.util.{Failure, Success, Try}

@Singleton
class AccountController @Inject()(cc: ControllerComponents) extends BaseController(cc) {

  def signUp(): Action[JsValue] = Action(parse.json) { implicit request =>
    request.body.validate[CreateNewUserCommand].fold(
      errors => BadRequest(errors.mkString),
      command => Try(Dispatcher.Push(command)) match {
        case Success(_) => Ok("Created")
        case Failure(_ : IllegalArgumentException) => BadRequest("Validation error")
        case Failure(_) => Conflict("User exist")
      }
    )
  }

  def signIn(): Action[JsValue] = Action(parse.json) { implicit request =>
    class Response(val session: String)
    request.body.validate[SignInQuery].fold(
      errors => BadRequest,
      query => Try(Dispatcher.Query(query)) match {
        case Success(session) => session match {
          case Some(sValue) => HttpOk(new Response(sValue))
          case None => Forbidden
        }
        case Failure(_) => BadRequest
      }
    )
  }
}
