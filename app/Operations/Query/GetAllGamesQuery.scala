package Operations.Query

import CQRS.Base.QueryBase
import Operations.Entity.Game

final case class GetAllGamesQuery() extends QueryBase[Seq[Game]] {
  override def ExecuteResult(): Seq[Game] = {
    Repository.GetAll[Game]()
  }
}
