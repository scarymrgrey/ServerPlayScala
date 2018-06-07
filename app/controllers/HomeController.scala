package controllers

import CQRS.Base.Dispatcher
import Operations.Command.CreateNewUserCommand
import akka.Operations.Query.GetAllUsersQuery
import javax.inject._
import net.liftweb.json.DefaultFormats
import net.liftweb.json.Serialization.write
import play.api.libs.json.Json
import play.api.mvc._

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  implicit val formats = DefaultFormats
  /**
    * Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index() = Action { implicit request: Request[AnyContent] =>
    Dispatcher.Push(CreateNewUserCommand("Login", "Password"))
    val users = Dispatcher.Query(GetAllUsersQuery())
    val jsonString: String = write(users)
    Ok(jsonString)
  }
}
