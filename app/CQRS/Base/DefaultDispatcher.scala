package CQRS.Base

class DefaultDispatcher(val UnitOfWork: TUnitOfWork) {

  def Query[T](query: QueryBase[T]): T = {
    query.OnExecute(UnitOfWork)
    query.Result.asInstanceOf[T]
  }

  def Push(command: CommandBase) = {
    command.OnExecute(UnitOfWork)
  }
}
