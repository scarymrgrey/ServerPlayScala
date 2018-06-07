package CQRS.Base
class A{}

abstract class MessageBase() {
  private var unitOfWork : TUnitOfWork = _
  protected lazy val Repository: TRepository = unitOfWork.GetRepository()
  var Result: Any = Unit

  def OnExecute(_unitOfWork: TUnitOfWork): Unit = {
    Result = Unit
    unitOfWork = _unitOfWork
    Execute()
  }

  def Execute()
}
