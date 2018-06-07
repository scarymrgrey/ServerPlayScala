package akka.Operations.Query

import java.util.Calendar

import CQRS.Base.QueryBase
import Operations.Entity.{Session, User}
import com.mongodb.casbah.Imports._

case class SignInQuery(Login: String, Password: String) extends QueryBase[Option[String]] {

  override def ExecuteResult(): Option[String] = {
    val criteria = MongoDBObject("Login" -> Login,
      "Password" -> Password)

    val option = Repository.GetSome[User](criteria).headOption
    option.map(z => {
      Repository.Save[Session](Session(z._id, Calendar.getInstance.getTime)).toString
    })
  }
}
