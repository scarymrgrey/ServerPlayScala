package Operations.Entity

import CQRS.Provider.MongoDB.MongoEntity


final case class Game(
                  var next_step: String = null,
                  var won: String= null,
                  var finished: String= null,
                  var players: Array[String]= null,
                  var steps: Int= 0,
                  var size: Array[Int]= null,
                  var crosses_length_to_win: Int= 0,
                  var field: Array[Array[Int]]= null,
                  var _id: String = null
                ) extends MongoEntity





