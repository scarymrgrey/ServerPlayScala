package CQRS.Provider.MongoDB

import CQRS.Base.TRepository
import com.mongodb.casbah.MongoCollection
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.{DBObject, FongoDB, WriteResult}
import org.bson.types.ObjectId
import org.json4s._
import org.json4s.native.JsonMethods._

import scala.collection.JavaConverters._
import scala.reflect.runtime.universe
import scala.reflect.runtime.universe._

class MongoRepository(db: => FongoDB) extends TRepository {

  implicit val formats = DefaultFormats

  override def GetAll[T: Manifest]()(implicit t: TypeTag[T]): Seq[T] = {
    val collection = db.getCollection(t.tpe.toString)
    collection.find()
      .toArray
      .asScala
      .map(r => parse(r.toString).extract[T])
  }

  override def GetById[T: Manifest](id: String)(implicit t: TypeTag[T]): Option[T] = {
    new MongoCollection(db.getCollection(t.tpe.toString))
      .findOneByID(new ObjectId(id))
      .map(a => parse(a.toString).extract[T])
  }

  override def Save[T](_entity: T)(implicit p: TypeTag[T]): Object = {
    val collection = db.getCollection(p.tpe.toString)
    val entity = _entity.asInstanceOf[MongoEntity].MongoEntity()
    val result: WriteResult = collection.save(entity)
    entity.get("_id")
  }

  override def GetSome[T: Manifest](predicate: DBObject)(implicit t: universe.TypeTag[T]): Seq[T] = {
    val collection = db.getCollection(t.tpe.toString)
    collection.find(predicate)
      .toArray
      .asScala
      .map(r => parse(r.toString).extract[T])
  }
}