package controllers

import CQRS.Base.Dispatcher
import Operations.Entity.{Session, User}
import Operations.Query.GetEntityById
import net.liftweb.json.DefaultFormats
import net.liftweb.json.Serialization.write
import play.api.mvc.Results.Forbidden
import play.api.mvc._

case class AuthRequest(user: User, request: Request[AnyContent])
  extends WrappedRequest(request)

trait AuthController {
  def Authenticated(action: AuthRequest => Result): Action[AnyContent] = {
    Action { request =>

      val forbid = Forbidden("You're not allowed to access this resource.")
      request.headers.get("session") match {
        case Some(session) => Dispatcher.Query(GetEntityById[Session](session.toString)) match {
          case Some(s) => Dispatcher.Query(GetEntityById[User](s.UserId))
            .map(user => action(AuthRequest(user, request))).get
          case None => forbid
        }
        case None => forbid
      }
    }
  }
}

abstract class BaseController(cc: ControllerComponents) extends AbstractController(cc) with AuthController {
  implicit val formats = DefaultFormats

  def HttpOk[T](res: T) = {
    Ok(write(res))
  }

}
