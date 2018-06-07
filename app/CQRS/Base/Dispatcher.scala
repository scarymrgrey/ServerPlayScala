package CQRS.Base

import CQRS.Provider.MongoDB.MongoUnitOfWork

object Dispatcher {

  private val dispatcher = new DefaultDispatcher(new MongoUnitOfWork)

  def Query[T](query: QueryBase[T]) = dispatcher.Query(query)

  def Push(command: CommandBase) = dispatcher.Push(command)

}
