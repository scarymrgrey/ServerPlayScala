package Operations.Query

import java.util.Calendar

import CQRS.Base.QueryBase
import Operations.Entity.{Game, Session, User}
import com.mongodb.casbah.commons.MongoDBObject

case class Response(username: String, online: Boolean, wins: Int, losses: Int)

final case class GetUserByNameQuery(login: String) extends QueryBase[Option[Response]] {
  override def ExecuteResult(): Option[Response] = {
    Repository.GetSome[User](MongoDBObject("login" -> login)).headOption match {
      case Some(usr) =>
        val tenSecBeforeNow = Calendar.getInstance()
        tenSecBeforeNow.add(Calendar.SECOND, -10)
        val session = Repository.GetSome[Session](MongoDBObject("UserId" -> usr._id)) headOption
        val active  = for (x <- session) yield x.CDate.before(tenSecBeforeNow.getTime)
       // val gamesWin = Repository.GetSome[Game](MongoDBObject("won" -> login,"finished"-> true)).count
        Option(Response(usr.login, active.getOrElse(false), 0, 0))

      case None => None
    }
  }
}
