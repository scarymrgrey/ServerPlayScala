package Operations.Query

import java.math.BigInteger
import java.security.MessageDigest
import CQRS.Base.QueryBase

final case class GetHashFromStringQuery(data : String) extends QueryBase[String] {
  override def ExecuteResult(): String = {
    String.format("%032x", new BigInteger(1, MessageDigest.getInstance("SHA-256")
      .digest(data.getBytes("UTF-8"))))
  }
}
