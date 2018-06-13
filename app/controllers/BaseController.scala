package controllers

import java.util.Calendar

import CQRS.Base.Dispatcher
import Operations.Command.{DeleteEntityCommand, ExtendSessionCommand}
import Operations.Entity.{Session, User}
import Operations.Query.{GetEntityById, GetSessionByUUIDQuery}
import net.liftweb.json.DefaultFormats
import net.liftweb.json.Serialization.write
import play.api.mvc.{Action, _}

case class AuthRequest[A](user: User, request: Request[A])
  extends WrappedRequest(request)

abstract class BaseController(cc: ControllerComponents) extends AbstractController(cc) {
  implicit val formats = DefaultFormats

  def HttpOk[T](res: T): Result = {
    val str = write(res).replace("_id", "id")
    Ok(str)
  }

  def Authenticated[A](bodyParser: BodyParser[A])(action: AuthRequest[A] => Result): Action[A] = Action(bodyParser) { implicit request =>
    val forbid = Forbidden("You're not allowed to access this resource.")
    request.headers.get("session") match {
      case Some(session) => Dispatcher.Query(GetSessionByUUIDQuery(session.toString)) match {
        case Some(s) => {
          val tenSecBeforeNow = Calendar.getInstance()
          tenSecBeforeNow.add(Calendar.SECOND, -10)
          if (s.CDate.before(tenSecBeforeNow.getTime)) {
            Dispatcher.Push(DeleteEntityCommand[Session](s._id.toString))
            Unauthorized
          } else {
            Dispatcher.Push(ExtendSessionCommand(s._id.toString))
            Dispatcher.Query(GetEntityById[User](s.UserId))
              .map(user => action(AuthRequest(user, request)))
              .get
          }
        }
        case None => forbid
      }
      case None => Unauthorized
    }
  }

}
