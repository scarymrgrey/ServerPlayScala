package Operations.Entity

import java.util.Date

import CQRS.Provider.MongoDB.MongoEntity
import org.json4s.JObject

final case class Session(UserId: String, CDate: Date) extends MongoEntity {

  def this(UserId: String, CDate: Date, _id: JObject) {
    this(UserId, CDate)
    this._id = _id.obj.head match { case (_, b) => b.values.toString }
  }
}


