package controllers

import java.util.Calendar

import CQRS.Base.Dispatcher
import Operations.Command.{DeleteEntityCommand, ExtendSessionCommand}
import Operations.Entity.{Session, User}
import Operations.Query.{GetEntityById, GetSessionByUUIDQuery}
import net.liftweb.json.DefaultFormats
import net.liftweb.json.Serialization.write
import play.api.libs.json.JsValue
import play.api.mvc.{Action, _}

case class AuthRequest(user: User, request: Request[JsValue])
  extends WrappedRequest(request)

abstract class BaseController(cc: ControllerComponents) extends AbstractController(cc)  {
  implicit val formats = DefaultFormats

  def HttpOk[T](res: T) = {
    Ok(write(res))
  }

  def Authenticated(action: AuthRequest => Result): Action[JsValue] = Action(parse.json) {implicit request =>
    val forbid = Forbidden("You're not allowed to access this resource.")
    request.headers.get("session") match {
      case Some(session) => Dispatcher.Query(GetSessionByUUIDQuery(session.toString)) match {
        case Some(s) => {
          val tenSecBeforeNow = Calendar.getInstance()
          tenSecBeforeNow.add(Calendar.SECOND, -200)
          if (s.CDate.before(tenSecBeforeNow.getTime)) {
            Dispatcher.Push(DeleteEntityCommand[Session](s._id))
            forbid
          } else {
            Dispatcher.Push(ExtendSessionCommand(s._id))
            Dispatcher.Query(GetEntityById[User](s.UserId))
              .map(user => action(AuthRequest(user, request))).get
          }
        }
        case None => forbid
      }
      case None => forbid
    }
  }

}
