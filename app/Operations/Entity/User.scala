package Operations.Entity

import CQRS.Provider.MongoDB.MongoEntity
import org.json4s.JObject

final case class User(login: String, password: String) extends MongoEntity {

  def this (login: String, password: String,_id : JObject) {
    this(login, password)
    this._id = _id.obj.toString
  }
}


