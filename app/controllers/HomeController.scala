package controllers

import AuthActionBuilder.UserAuth
import CQRS.Base.Dispatcher
import akka.Operations.Query.GetAllUsersQuery
import javax.inject._
import play.api.mvc._

@Singleton
class HomeController @Inject()(AuthAction: UserAuth, cc: ControllerComponents) extends BaseController(cc) {

  def index(): Action[AnyContent] = Authenticated { implicit request: AuthRequest =>
    //HttpOk(Dispatcher.Query(GetAllUsersQuery()))
    HttpOk(request.user)
  }
}
