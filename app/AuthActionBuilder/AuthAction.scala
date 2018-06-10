package AuthActionBuilder

import CQRS.Base.Dispatcher
import Operations.Entity.Session
import Operations.Query.GetEntityById
import javax.inject.Inject
import play.api.mvc.Results._
import play.api.mvc.{Result, _}

import scala.concurrent.{ExecutionContext, Future}

class UserRequest[A](val username: Option[String], request: Request[A]) extends WrappedRequest[A](request)

class UserAuth @Inject()(val parser: BodyParsers.Default)(implicit val executionContext: ExecutionContext)
  extends ActionBuilder[UserRequest, AnyContent] with ActionTransformer[Request, UserRequest] {
  //override def

  def transform[A](request: Request[A]): Future[UserRequest[A]] = {
    val forbid = Forbidden("You're not allowed to access this resource.")
    request.headers.get("session") match {
      case Some(session) => Dispatcher.Query(GetEntityById[Session](session.toString)) match {
        case Some(_) => Future.successful {
          new UserRequest(request.session.get("username"), request)
        }
        case None => Future.successful {
          new UserRequest(request.session.get("username"), request)
        }
      }
      case None => Future.successful {
        new UserRequest(request.session.get("username"), request)
      }
    }
  }

  def invokeBlock[A](request: Request[A], block: Request[A] => Future[Result]) = {
    val forbid = Forbidden("You're not allowed to access this resource.")
    request.headers.get("session") match {
      case Some(session) => Dispatcher.Query(GetEntityById[Session](session.toString)) match {
        case Some(_) => block(request)
        case None => Future(forbid)
      }
      case None => Future(forbid)
    }
  }
}


