package controllers

import CQRS.Base.Dispatcher
import akka.Operations.Query.GetAllUsersQuery
import javax.inject._
import play.api.mvc._

@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends BaseController(cc) {

  def index() = Action { implicit request: Request[AnyContent] =>
    HttpOk(Dispatcher.Query(GetAllUsersQuery()))
  }
}
