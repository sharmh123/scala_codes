package TestCodesScala.games

import javax.swing.JOptionPane
import scala.io.Source
import scala.util.Random

case class WordMeaning(word: String, meaning: String) {
  override def toString(): String = {
    return s"$word: $meaning"
  }
}

class WordMeaningsForKids(inputFile: String) {
  def runGame() = {
    val lines = Source.fromFile(inputFile)
      .getLines()
      .toList
    var wordMeanings = Seq[WordMeaning]()
    lines.foreach {
      line =>
        val splits = line.split(",", 2)
        if (splits.size > 1) {
          val meaning = splits(1).split("\\.", 2)(1)
          val wordMeaning = WordMeaning(splits(0), meaning)
          wordMeanings = wordMeanings :+ wordMeaning
        }
    }

    while (true) {
      val random = Random.nextInt(wordMeanings.size)
      val wordMeaning = wordMeanings(random)
      JOptionPane.showMessageDialog(null,
        wordMeaning.word,
        s"word",
        JOptionPane.PLAIN_MESSAGE)
      println(wordMeaning)
    }
  }
}

object WordMeaningsForKids {
  def main(args: Array[String]): Unit = {
    val inputFile = "/Users/himash/Downloads/word meanings for kids"
    new WordMeaningsForKids(inputFile).runGame()
  }
}
