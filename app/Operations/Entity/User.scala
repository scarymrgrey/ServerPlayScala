package Operations.Entity

import CQRS.Provider.MongoDB.MongoEntity
import org.json4s.{DefaultFormats, JObject}

final case class User(login: String, password: String) extends MongoEntity {
  implicit val formats = DefaultFormats
  def this(login: String, password: String, _id: JObject) {
    this(login, password)
    this._id = _id.obj.head match { case (_, b) => b.values.toString }
  }
}


