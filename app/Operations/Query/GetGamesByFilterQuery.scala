package Operations.Query

import CQRS.Base.QueryBase
import Operations.Entity.Game


final case class GetGamesByFilterQuery(limit: Int,offset : Int) extends QueryBase[Seq[Game]] {
  override def ExecuteResult(): Seq[Game] = {
    require(limit>0)
    require(offset>=0)
    val games = Repository.GetAll[Game]().slice(offset, offset + limit)
    games
  }
}
