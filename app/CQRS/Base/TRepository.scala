package CQRS.Base

import com.mongodb.DBObject

import scala.reflect.runtime.universe._

trait TRepository {
  def GetAll[T: Manifest]()(implicit t: TypeTag[T]): Seq[T]

  def GetSome[T: Manifest](predicate: DBObject)(implicit t: TypeTag[T]): Seq[T]

  def Save[T](entity: T)(implicit p: TypeTag[T]): Object
}