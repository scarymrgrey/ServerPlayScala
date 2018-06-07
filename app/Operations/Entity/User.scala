package Operations.Entity

import CQRS.Provider.MongoDB.MongoEntity

final case class User(Login: String, Password: String) extends MongoEntity
