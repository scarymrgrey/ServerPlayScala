package Operations.Query

import CQRS.Base.{Dispatcher, QueryBase}
import Operations.Entity.User


final case class GetUsersByFilterQuery(limit: Int, offset: Int) extends QueryBase[Seq[User]] {
  override def ExecuteResult(): Seq[User] = {
    require(limit > 0)
    require(offset >= 0)
    val users = Repository.GetAll[User]().map(r => Dispatcher.Query(GetUserByNameQuery(r.login))).slice(offset, offset + limit)
    users
  }
}
