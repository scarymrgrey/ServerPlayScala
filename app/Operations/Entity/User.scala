package Operations.Entity

import CQRS.Provider.MongoDB.MongoEntity
import org.json4s.JObject

final case class User(login: String, password: String) extends MongoEntity {
  var _id : String = _
  //def apply(login: String, password: String, _id: String): User = new User(login, password, _)
  def this (login: String, password: String,_id : JObject) {
    this(login, password)
    this._id = _id.obj.toString
    val y = 6
  }
}
