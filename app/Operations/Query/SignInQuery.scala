package Operations.Query

import java.util.{Calendar, UUID}

import CQRS.Base.{Dispatcher, QueryBase}
import Operations.Entity.{Session, User}
import com.mongodb.casbah.Imports._
import play.api.libs.json.Json

object SignInQuery {
  implicit val fmt = Json.format[SignInQuery]
}

case class SignInQuery(username: String, password: String) extends QueryBase[Option[String]] {

  override def ExecuteResult(): Option[String] = {
    require((8 until 100) contains password.length, "tut tebe ne tam, siriuzly :P")
    require((3 until 20) contains username.length, "tut tebe ne tam, siriuzly :P")

    val hash = Dispatcher.Query(GetHashFromStringQuery(password))
    val criteria = MongoDBObject("login" -> username, "password" -> hash)

    Repository.GetSome[User](criteria)
      .headOption
      .map(z => {
        Repository.DeleteSome[Session](MongoDBObject("UserId" -> z._id))
        val uuid = UUID.randomUUID().toString
        val session = Session(z._id, uuid, Calendar.getInstance.getTime)
        Repository.Save[Session](session)
        uuid
      })
  }
}
