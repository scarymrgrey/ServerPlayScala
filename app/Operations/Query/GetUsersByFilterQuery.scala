package Operations.Query

import CQRS.Base.QueryBase
import Operations.Entity.User


final case class GetUsersByFilterQuery(limit: Int,offset : Int) extends QueryBase[Seq[User]] {
  override def ExecuteResult(): Seq[User] = {
    require(limit>0)
    require(offset>=0)
    val users = Repository.GetAll[User]().slice(offset, offset + limit)
    users
  }
}
