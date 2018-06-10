package CQRS.Provider.MongoDB

import CQRS.Base.TRepository
import com.mongodb.casbah.Imports.MongoDBObject
import com.mongodb.casbah.MongoCollection
import com.mongodb.{DBObject, FongoDB}
import org.bson.types.ObjectId
import org.json4s._
import org.json4s.native.JsonMethods._

import scala.collection.JavaConverters._
import scala.reflect.runtime.universe
import scala.reflect.runtime.universe._

class MongoRepository(db: => FongoDB) extends TRepository {

  implicit val formats = DefaultFormats

  override def GetAll[T: Manifest]()(implicit t: TypeTag[T]): Seq[T] = {
    val collection = db.getCollection(t.tpe.toString).find()
    val result = collection
      .toArray
      .asScala
      .map(r => parse(r.toString).extract[T])
    collection.close()
    result
  }

  override def GetById[T: Manifest](id: String)(implicit t: TypeTag[T]): Option[T] = {
    new MongoCollection(db.getCollection(t.tpe.toString))
      .findOneByID(new ObjectId(id))
      .map(a => parse(a.toString).extract[T])
  }

  override def Save[T: Manifest](_entity: T)(implicit p: TypeTag[T]): Object = {
    val collection = db.getCollection(p.tpe.toString)
    val entity = _entity.asInstanceOf[MongoEntity].MongoEntity()
    collection.save(entity)
    entity.get("_id")
  }

  override def GetSome[T: Manifest](predicate: DBObject)(implicit t: universe.TypeTag[T]): Seq[T] = {
    val collection = db.getCollection(t.tpe.toString).find(predicate)
    val ts = collection
      .toArray
      .asScala
      .map(r => parse(r.toString).extract[T])
    collection.close()
    ts
  }

  override def DeleteById[T: Manifest](id: String)(implicit p: universe.TypeTag[T]): Unit = {
    val collection = db.getCollection(p.tpe.toString)
    collection.findAndRemove(MongoDBObject("_id" -> new ObjectId(id)))
  }

  override def Modify[T: Manifest](_entity: T)(implicit p: universe.TypeTag[T]): Unit = {
    val collection = db.getCollection(p.tpe.toString)
    val entity = _entity.asInstanceOf[MongoEntity]
    val bObject = entity.MongoEntity()
    bObject.removeField("_id")
    collection.findAndModify(
      MongoDBObject("_id" -> new ObjectId(entity._id)),
      bObject)
  }
}