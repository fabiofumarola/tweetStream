package it.prova

import akka.stream.ActorMaterializer
import akka.util.Timeout
import net.fehmicansaglam.bson.BsonDocument
import net.fehmicansaglam.tepkin.MongoClient

import scala.concurrent.duration._
import scala.util.Try

/**
  * Created by fabiofumarola on 21/02/16.
  */
object Main extends App {

  val connName = Try(args(0)).getOrElse("localhost")
  val dbName = Try(args(1)).getOrElse("twi")
  val collName = Try(args(2)).getOrElse("")

  val mongoSource = new MongoSource(
    connName = connName,
    dbName = dbName,
    collName = collName
  )


  implicit val timeout: Timeout = 5.seconds
  implicit val mat = ActorMaterializer()

  mongoSource.stream()
    .map(b => b.size)
    .runForeach(println)
}
