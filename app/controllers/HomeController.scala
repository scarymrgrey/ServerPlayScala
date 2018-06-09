package controllers

import AuthActionBuilder.AuthAction
import CQRS.Base.Dispatcher
import akka.Operations.Query.GetAllUsersQuery
import javax.inject._
import play.api.mvc._

@Singleton
class HomeController @Inject()(AuthAction: AuthAction, cc: ControllerComponents) extends BaseController(cc) {

  def index() = AuthAction { implicit request: Request[AnyContent] =>
    this.AuthAction.CurrentUser
    HttpOk(Dispatcher.Query(GetAllUsersQuery()))
  }
}
