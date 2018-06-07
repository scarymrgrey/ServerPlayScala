package CQRS.Provider.MongoDB

import com.mongodb.casbah.commons.Imports.DBObject
import com.mongodb.casbah.commons.MongoDBObject
import net.liftweb.json.DefaultFormats
import net.liftweb.json.Serialization.write

trait MongoEntity {
  var _id: String = _

  def MongoEntity(): DBObject = {
    implicit val formats = DefaultFormats
    val jsonString = write(this)
    MongoDBObject(jsonString)
  }
}

