package Operations.Query

import java.util.Calendar

import CQRS.Base.QueryBase
import Operations.Entity.{Session, User}
import com.mongodb.casbah.Imports._
import play.api.libs.json.Json

object SignInQuery {
  implicit val fmt = Json.format[SignInQuery]
}

case class SignInQuery(Login: String, Password: String) extends QueryBase[Option[String]] {

  override def ExecuteResult(): Option[String] = {

    val criteria = MongoDBObject("login" -> Login, "password" -> Password)

    Repository.GetSome[User](criteria)
      .headOption
      .map(z => {
        val session = Session(z._id, Calendar.getInstance.getTime)
        Repository.Save[Session](session).toString
      })

  }
}
