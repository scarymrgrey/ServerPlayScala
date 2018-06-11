package controllers

import CQRS.Base.Dispatcher
import Operations.Query.GetAllGamesQuery
import javax.inject._
import play.api.mvc._

@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends BaseController(cc) {

  def index(): Action[AnyContent] = Action { implicit request =>
    HttpOk(Dispatcher.Query(GetAllGamesQuery()))
    //HttpOk(request.user)
  }
}
