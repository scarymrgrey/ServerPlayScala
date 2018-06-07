package CQRS.Base

trait TUnitOfWork {
  def GetRepository(): TRepository
}
