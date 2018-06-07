package CQRS.Provider.MongoDB

import CQRS.Base.TRepository
import com.mongodb.{DBObject, FongoDB}
import org.json4s._
import org.json4s.native.JsonMethods._
import com.mongodb.casbah.Imports._

import scala.collection.JavaConverters._
import scala.reflect.runtime.universe
import scala.reflect.runtime.universe._

case class Child(name: String, age: Int, birthdate: Option[java.util.Date])

class MongoRepository(db: => FongoDB) extends TRepository {

  implicit val formats = DefaultFormats

  override def GetAll[T: Manifest]()(implicit t: TypeTag[T]): Seq[T] = {
    val collection = db.getCollection(t.tpe.toString)
    collection.find()
      .toArray
      .asScala
      .map(r => parse(r.toString).extract[T])
  }

  override def Save[T](_entity: T)(implicit p: TypeTag[T]): Object = {
    val collection = db.getCollection(p.tpe.toString)
    val entity = _entity.asInstanceOf[MongoEntity]
    collection.insert(entity.MongoEntity()).getUpsertedId
  }

  override def GetSome[T: Manifest](predicate: DBObject)(implicit t: universe.TypeTag[T]): Seq[T] = {
    val collection = db.getCollection(t.tpe.toString)
    collection.find(predicate)
      .toArray
      .asScala
      .map(r => parse(r.toString).extract[T])
  }
}