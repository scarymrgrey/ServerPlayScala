package CQRS.Base

 abstract class QueryBase[T] extends MessageBase() {
  override def Execute(): Unit = {
    Result = ExecuteResult()
  }
   def ExecuteResult() : T
 }
