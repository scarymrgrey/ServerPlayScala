package Operations.Command

import CQRS.Base.CommandBase
import Operations.Entity.User
import play.api.libs.json.Json
object CreateNewUserCommand{
  implicit val fmt = Json.format[CreateNewUserCommand]
}

final case class CreateNewUserCommand(Login: String, Password: String) extends CommandBase {
  override def Execute(): Unit = {
    val user = new User(Login, Password)
    Repository.Save(user)
  }
}
