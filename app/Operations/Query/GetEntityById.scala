package Operations.Query

import CQRS.Base.QueryBase


final case class GetEntityById[T: Manifest](Id: String) extends QueryBase[Option[T]] {
  override def ExecuteResult(): Option[T] = {
    Repository.GetById(Id)
  }
}
