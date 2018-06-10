package CQRS.Base

import com.mongodb.DBObject

import scala.reflect.runtime.universe
import scala.reflect.runtime.universe._

trait TRepository {
  def GetAll[T: Manifest]()(implicit t: TypeTag[T]): Seq[T]

  def GetSome[T: Manifest](predicate: DBObject)(implicit t: TypeTag[T]): Seq[T]

  def GetById[T: Manifest](id: String)(implicit t: TypeTag[T]): Option[T]

  def Save[T: Manifest](entity: T)(implicit p: TypeTag[T]): Object

  def DeleteById[T: Manifest](id: String)(implicit p: TypeTag[T])

  def DeleteSome[T: Manifest](predicate: DBObject)(implicit p: universe.TypeTag[T])

  def Modify[T: Manifest](entity: T)(implicit p: universe.TypeTag[T])
}