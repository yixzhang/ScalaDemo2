package main.scala.ch3_mongodb_driver

import com.mongodb.Mongo

/**
 * Created with IntelliJ IDEA.
 * User: zhangyi
 * Date: 13-7-29
 * Time: 上午11:03
 * To change this template use File | Settings | File Templates.
 */
class MongoClient (val host:String, val port:Int){

  require(host != null, "You have to provide a host name")
  private val underlying = new Mongo(host, port)
  def this() = this("127.0.0.1", 27017)

  def version = underlying.getVersion

  def dropDB(name:String) = underlying.dropDatabase(name)

  def createDB(name:String) = DB(underlying.getDB(name))

  def db(name:String) = DB(underlying.getDB(name))

}
