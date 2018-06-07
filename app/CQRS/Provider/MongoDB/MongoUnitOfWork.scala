package CQRS.Provider.MongoDB

import CQRS.Base.{TRepository, TUnitOfWork}
import com.github.fakemongo.Fongo

class MongoUnitOfWork  extends TUnitOfWork{
  val fongo = new Fongo("test")
  lazy val _db = fongo.getDB("test")
  override def GetRepository(): TRepository = {
    new MongoRepository(_db)
  }
}
