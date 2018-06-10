package Operations.Command

import CQRS.Base.CommandBase
import Operations.Entity.User
import com.mongodb.casbah.Imports.MongoDBObject
import play.api.libs.json.Json

object CreateNewUserCommand {
  implicit val fmt = Json.format[CreateNewUserCommand]
}

final case class CreateNewUserCommand(Login: String, Password: String) extends CommandBase {
  @throws(classOf[Exception])
  override def Execute(): Unit = {
    Repository.GetSome[User](MongoDBObject("login" -> Login)) headOption match {
      case Some(_) =>throw new Exception
      case None => Repository.Save(User(Login, Password))
    }
  }
}
