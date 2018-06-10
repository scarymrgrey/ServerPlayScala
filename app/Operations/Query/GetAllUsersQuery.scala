package Operations.Query

import CQRS.Base.QueryBase
import Operations.Entity.User

final case class GetAllUsersQuery() extends QueryBase[Seq[User]] {
  override def ExecuteResult(): Seq[User] = {
    Repository.GetAll[User]()
  }
}
