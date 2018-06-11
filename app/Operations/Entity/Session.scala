package Operations.Entity

import java.util.Date

import CQRS.Provider.MongoDB.MongoEntity
import org.json4s.DefaultFormats

final case class Session(UserId: String, UUID: String, var CDate: Date, override var _id: String = null) extends MongoEntity {
  implicit val formats = DefaultFormats
}


