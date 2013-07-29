package main.scala.ch3_mongodb_driver

/**
 * Created with IntelliJ IDEA.
 * User: zhangyi
 * Date: 13-7-29
 * Time: 上午11:37
 * To change this template use File | Settings | File Templates.
 */
import com.mongodb.{DB => MongoDB}
import scala.collection.convert.Wrappers.JSetWrapper


class DB private (val underlying: MongoDB){
  private def collection(name: String) = underlying.getCollection(name)

  def readOnlyCollection(name: String) = new DBCollection(collection(name))
  def administrableCollection(name: String) = new DBCollection(collection(name))
    with Administrable
  def updatableCollection(name: String) = new DBCollection(collection(name))
    with Updatable

  def collectionNames = for(name <-
    new JSetWrapper[String](underlying.getCollectionNames))  yield  name
}

object DB{
  def apply(underlying: MongoDB) = new DB(underlying)
}