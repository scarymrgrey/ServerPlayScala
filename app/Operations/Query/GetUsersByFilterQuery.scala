package Operations.Query

import CQRS.Base.{Dispatcher, QueryBase}
import Operations.Entity.User

final case class GetUsersByFilterQuery(limit: Int, offset: Int) extends QueryBase[Seq[UserResponse]] {
  override def ExecuteResult(): Seq[UserResponse] = {
    require(limit > 0)
    require(offset >= 0)
    Repository.GetAll[User]()
      .map(r => Dispatcher.Query(GetUserByNameQuery(r.login)).get)
      .slice(offset, offset + limit)
  }
}
