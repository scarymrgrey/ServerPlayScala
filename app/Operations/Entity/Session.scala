package Operations.Entity

import java.util.{Calendar, Date}

import CQRS.Provider.MongoDB.MongoEntity

 final case class Session( UserId: String,CDate : String) extends MongoEntity
