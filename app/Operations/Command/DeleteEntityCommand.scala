package Operations.Command

import CQRS.Base.CommandBase
import Operations.Entity.User
import play.api.libs.json.Json

final case class DeleteEntityCommand[T: Manifest](id: String) extends CommandBase {
  override def Execute(): Unit = {
    Repository.DeleteById[T](id)
  }
}
