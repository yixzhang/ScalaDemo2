package java_one_akka_demo

import akka.actor.{ActorSystem, Props, Actor}
import scala.concurrent.duration.Duration

/**
 * Created with IntelliJ IDEA.
 * User: zhangyi
 * Date: 13-8-4
 * Time: 下午5:28
 * To change this template use File | Settings | File Templates.
 */
object WordCount {

  case class CreateWord(word: String)
  case class GetWordCount(previewLetters: String)
  case class ReturnWordCount(word: String, count: Int)
  case object Print

  class LetterActor extends Actor{
    var count = 0
    def receive = {
      case CreateWord(word) =>
        if(word.isEmpty) count += 1
        else{
          val firstLetter = word.head.toString
          context.child(firstLetter).
            getOrElse(context.actorOf(Props[LetterActor],
            firstLetter)) ! CreateWord(word.tail)
        }

      case GetWordCount(previewLetters) =>
        if(context.children.isEmpty){
          sender !ReturnWordCount(previewLetters + context.self.path.name , count)
        }
        else{
          for(child <- context.children){
            child !  GetWordCount(previewLetters + context.self.path.name)
          }
        }

      case ReturnWordCount(word, count) =>
        context.parent ! ReturnWordCount(word, count)
    }
  }

  class WordActor extends Actor{
    def receive = {
      case CreateWord(word) =>
        val firstLetter = word.head.toString
        context.child(firstLetter).
          getOrElse(context.actorOf(Props[LetterActor],
          firstLetter)) ! CreateWord(word.tail)

      case GetWordCount(previewLetters) =>
        for(child <- context.children){
          child ! GetWordCount(previewLetters)
        }

      case ReturnWordCount(word, count) =>
        context.parent ! ReturnWordCount(word, count)
    }
  }

  class MainActor extends Actor{
    val root = context.actorOf(Props[WordActor], "wordActor")
    def receive = {
      case cw: CreateWord => root !cw
      case ReturnWordCount(word, count) =>
        println("%s:%s".format(word, count))
      case Print => root! GetWordCount("")
    }
  }

  def main(args: Array[String]){
    val system = ActorSystem("WordCount")
    val main = system.actorOf(Props[MainActor], "mainActor")
    main ! CreateWord("hello")
    1 to 3 foreach { c => main ! CreateWord("hi")}
    main ! CreateWord("hello")
    main ! CreateWord("helium")
    main ! Print
  }

}
