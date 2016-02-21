package it.prova

import akka.actor.ActorRef
import akka.stream.scaladsl.Source
import akka.util.Timeout
import net.fehmicansaglam.bson.BsonDocument
import net.fehmicansaglam.tepkin.MongoClient
import scala.concurrent.duration._

/**
  * Created by fabiofumarola on 21/02/16.
  */
class MongoSource(connName: String,
                  dbName: String,
                  collName: String) {

  val client = MongoClient(s"mongodb://$connName")
  val db = client(dbName)
  val collection = db(collName)

  implicit val timeout: Timeout = 30.seconds

  def stream(): Source[List[BsonDocument], ActorRef] = {
    collection.find(BsonDocument.empty, tailable = true)
  }

}
