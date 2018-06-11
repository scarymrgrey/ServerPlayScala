package Operations.Entity

import CQRS.Provider.MongoDB.MongoEntity
import org.json4s.DefaultFormats

 case class User(login: String, password: String, var _id: String = null) extends MongoEntity {
  implicit val formats = DefaultFormats
}


