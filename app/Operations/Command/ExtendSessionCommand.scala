package Operations.Command

import java.util.Calendar

import CQRS.Base.CommandBase
import Operations.Entity.Session

final case class ExtendSessionCommand(id: String) extends CommandBase {
  override def Execute(): Unit = {
    Repository.GetById[Session](id).foreach { s =>
      s.CDate = Calendar.getInstance().getTime
      Repository.Modify[Session](s)
    }
  }
}
