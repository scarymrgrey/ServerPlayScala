package Operations.Command

import CQRS.Base.CommandBase
import Operations.Entity.User

final case class CreateNewUserCommand(Login: String, Password: String) extends CommandBase {
  override def Execute(): Unit = {
    Repository.Save(User(Login, Password))
  }
}
