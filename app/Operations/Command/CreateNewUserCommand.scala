package Operations.Command

import CQRS.Base.{CommandBase, Dispatcher}
import Operations.Entity.User
import Operations.Query.GetHashFromStringQuery

import com.mongodb.casbah.Imports.MongoDBObject
import play.api.libs.json.Json

object CreateNewUserCommand {
  implicit val fmt = Json.format[CreateNewUserCommand]
}

final case class CreateNewUserCommand(username: String, password: String) extends CommandBase {
  @throws(classOf[Exception])
  override def Execute(): Unit = {
    require((8 until 100) contains password.length)
    require((3 until 20) contains username.length)
    Repository.GetSome[User](MongoDBObject("login" -> username)) headOption match {
      case Some(_) => throw new Exception
      case None => {
        val hash = Dispatcher.Query(GetHashFromStringQuery(password))
        Repository.Save(User(username, hash))
      }
    }
  }
}
