package Operations.Command

import CQRS.Base.CommandBase
import Operations.Entity._
import com.mongodb.casbah.commons.MongoDBObject

final case class LogOutCommand(user: User) extends CommandBase {
  override def Execute(): Unit = {
    Repository.DeleteSome[Session](MongoDBObject("UserId"->user._id))
  }
}
