package TestCodesScala.games

import java.util.Scanner
import javax.swing.JOptionPane
import scala.io.Source
import scala.util.Random

class WhoSaidWhat(sentenceFile: String, numPersons: Int) {
  val baseDir = "/Users/himanshusharma/IdeaProjects/resources/DNB/sentences"

  def runGame(): Int = {
    val personSentences = getMatches()
    val options: Array[AnyRef] = Array("OK", "Restart")
    personSentences.foreach { personSentence =>
      JOptionPane.showMessageDialog(null,
                                    personSentence,
                                    "",
                                    JOptionPane.PLAIN_MESSAGE)
    }

    while (true) {
      Random.shuffle(personSentences).foreach { personSentence =>
        val result = JOptionPane.showOptionDialog(null,
                                                  s"${personSentence.person}?",
                                                  "",
                                                  JOptionPane.YES_NO_OPTION,
                                                  JOptionPane.PLAIN_MESSAGE,
                                                  null,
                                                  options,
                                                  options(0))
        if (result == JOptionPane.NO_OPTION) {
          runGame()
        }
        println(personSentence)
      }
      println("============")
    }
    return 0
  }

  def findNextIndex(previousIndex: Int): Int = {
    var index = 0
    do {
      index = Random.nextInt(numPersons)
    } while (index == previousIndex)

    return index
  }

  def getMatches(): List[PersonSentenceMap] = {
    val namesFile = s"$baseDir/names.csv"
    val sentenceFilePath = s"$baseDir/$sentenceFile"
    val names = getItems(namesFile)
    val sentences = getItems(sentenceFilePath)
    var personSentenceMapping = List[PersonSentenceMap]()
    (0 to (names.size - 1)).foreach { i =>
      personSentenceMapping = personSentenceMapping :+ PersonSentenceMap(
        names(i),
        sentences(i))
    }
    personSentenceMapping
  }

  def getItems(fileName: String): List[String] = {
    val names = Random
      .shuffle(Source.fromFile(fileName).getLines())
      .slice(0, numPersons)
      .toList
    names
  }
}

object WhoSaidWhat {
  def main(args: Array[String]): Unit = {
    val scanner = new Scanner(System.in)
    //println("Sentence File: <press enter to choose a default file.>")
    var sentenceFile = "testSentences"
      // scanner.nextLine()
//    sentenceFile =
//      if (sentenceFile.trim.isEmpty) "testSentences" else sentenceFile

    var numPersons = 5
    print(s"Num Persons: (default=$numPersons)")
    val numPersonsInput = scanner.nextLine()
    if (numPersonsInput.nonEmpty)
      numPersons = numPersonsInput.trim.toInt
    new WhoSaidWhat(sentenceFile, numPersons).runGame()
  }
}
