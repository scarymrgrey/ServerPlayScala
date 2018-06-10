package Operations.Query

import CQRS.Base.QueryBase
import Operations.Entity.Session
import com.mongodb.casbah.Imports._


final case class GetSessionByUUIDQuery(uuid: String) extends QueryBase[Option[Session]] {

  override def ExecuteResult(): Option[Session] = {
    val criteria = MongoDBObject("UUID" -> uuid)
    Repository.GetSome[Session](criteria)
      .headOption
  }
}
