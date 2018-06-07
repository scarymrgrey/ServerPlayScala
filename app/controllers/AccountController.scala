package controllers

import CQRS.Base.Dispatcher
import Operations.Command.CreateNewUserCommand
import Operations.Query.SignInQuery
import javax.inject._
import play.api.libs.json.JsValue
import play.api.mvc._

@Singleton
class AccountController @Inject()(cc: ControllerComponents) extends BaseController(cc) {

  def signUp(): Action[JsValue] = Action(parse.json) { implicit request =>
    request.body.validate[CreateNewUserCommand].fold(
      errors => BadRequest(errors.mkString),
      command => HttpOk(Dispatcher.Push(command))
    )
  }

  def signIn() : Action[JsValue] = Action(parse.json) { implicit request =>
    request.body.validate[SignInQuery].fold(
      errors => BadRequest(errors.mkString),
      query => HttpOk(Dispatcher.Query(query))
    )
  }
}
