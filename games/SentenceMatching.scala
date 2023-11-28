package TestCodesScala.games

import java.util.Scanner

import javax.swing.JOptionPane

import scala.io.Source
import scala.util.Random

case class PersonSentenceMap(person: String, sentence: String) {
  override def toString: String = s"$person :: $sentence"
}

class SentenceMatching(sentenceFile: String, numPersons: Int) {
  val baseDir = "/Users/himanshusharma/IdeaProjects/resources/DNB/sentences"

  def getItems(fileName: String): List[String] = {
    val names = Random.shuffle(Source.fromFile(fileName).getLines()).slice(0, numPersons).toList
    names
  }

  def getMatches(): List[PersonSentenceMap] = {
    val namesFile = s"$baseDir/names.csv"
    val sentenceFilePath = s"$baseDir/$sentenceFile"
    val names = getItems(namesFile)
    val sentences = getItems(sentenceFilePath)
    var personSentenceMapping = List[PersonSentenceMap]()
    (0 to (names.size - 1)).foreach { i =>
      personSentenceMapping = personSentenceMapping :+ PersonSentenceMap(names(i), sentences(i))
    }
    personSentenceMapping
  }

  def runGame() = {
    val personSentences = getMatches()
    personSentences.foreach {
      personSentence =>
        JOptionPane.showMessageDialog(null, personSentence, "", JOptionPane.PLAIN_MESSAGE)
    }

    while (true) {
      val index = Random.nextInt(numPersons)
      val personSentence = personSentences(index)
      JOptionPane.showMessageDialog(null,
        s"${personSentence.person}?", "", JOptionPane.PLAIN_MESSAGE)
      println(personSentence)
    }
  }
}

object SentenceMatching {
  def main(args: Array[String]): Unit = {
    val scanner = new Scanner(System.in)
    println("Sentence File: ")
    val sentenceFile = scanner.nextLine()
    println("Num Persons: ")
    val numPersons = scanner.nextInt()
    new SentenceMatching(sentenceFile, numPersons).runGame()
  }
}
