package Operations.Query

import CQRS.Base.QueryBase
import Operations.Entity.Game


final case class GetGameByIdQuery(Id: String) extends QueryBase[Game] {
  override def ExecuteResult(): Game = {
    val game = Repository.GetById[Game](Id)
    game.head
  }
}
