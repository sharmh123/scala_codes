package TestCodesScala.games

import java.util.Scanner

import javax.swing.JOptionPane

import scala.io.Source

class WordMeanings(startIndex: Int, endIndex: Int) {
  val fileName = "/Users/himash/testcodes2/src/Testcodes/src/resources/DNB/words/wordlist.csv"
  def runGame() = {
    val lines = Source.fromFile(fileName).getLines().map(x => x.split("->")).toList
    val wordMeanings = lines.slice(startIndex, endIndex)
    var count = startIndex
    wordMeanings.foreach {
      elem =>
        val word = elem(0)
        val meaning = elem(1)
        JOptionPane.showMessageDialog(null, word, s"word $count", JOptionPane.PLAIN_MESSAGE)
        println(s"$count) $word: $meaning")
        count += 1
    }
  }
}

object WordMeanings {
  def main(args: Array[String]): Unit = {
    val scanner = new Scanner(System.in)
    println("startIndex: ")
    val startIndex = scanner.nextInt()
    println("endIndex: ")
    val endIndex = scanner.nextInt()
    val wordMeanings = new WordMeanings(startIndex, endIndex)
    wordMeanings.runGame()
  }
}
