package controllers


import javax.inject._
import play.api.libs.json.JsValue
import play.api.mvc._

@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends BaseController(cc) {

  def index(): Action[JsValue] = Authenticated { implicit request: AuthRequest =>
    //HttpOk(Dispatcher.Query(GetAllUsersQuery()))
    HttpOk(request.user)
  }
}
