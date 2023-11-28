package TestCodesScala.games

import java.util.Scanner

import TestcodesJava.config.ConfigContainer
import TestcodesJava.core.DNBCore
import javax.swing.JOptionPane

import scala.io.Source
import scala.util.Random
import scala.collection.JavaConverters._

class DNBRepeatedWords(exerciseLength: Int) extends DNBGame {
  val configFileName = DNBCore.getDNBConfigDir + "/" + "repeatedWords_config.txt"
  val configContainer = new ConfigContainer(configFileName)

  override def generateExerciseWords(): Seq[String] = {
    val inputFile = configContainer.getInputFileName
    val exerciseLen = exerciseLength //configContainer.getExerciseLen
    val words = Source.fromFile(inputFile).getLines().toSeq
    val wordsLength = words.length
    var repeatWords = Seq[String]()
    val numWordsToRepeat = configContainer.getNumWordsToRepeat

    val randomGen = new Random()
    // Get the repeat words
    (0 to (numWordsToRepeat - 1)).foreach {
      index =>
        var randomNumber = randomGen.nextInt(wordsLength)
        while (repeatWords.contains(words(randomNumber))) {
          randomNumber = randomGen.nextInt(wordsLength)
        }

        repeatWords = repeatWords :+ words(randomNumber)
    }

    // Now generate the exercise words with the repeated words.
    var exerciseWords = Seq[String]()
    (0 to exerciseLen - 1).foreach {
      index =>
        val randomNumber = randomGen.nextInt(numWordsToRepeat)
        exerciseWords = exerciseWords :+ repeatWords(randomNumber)
    }
    exerciseWords
  }

  override def runGame(): Unit = {
    while (true) {
      val exerciseWords = generateExerciseWords()
      var counter = 1
      exerciseWords.foreach {
        word => JOptionPane.showMessageDialog(null, word, s"word: $counter", JOptionPane.PLAIN_MESSAGE)
          counter += 1
      }
      JOptionPane.showMessageDialog(null, "Repeat the words", s"Test",
        JOptionPane.PLAIN_MESSAGE)
      exerciseWords.foreach(word => print(word + " "))
      println("\n=========")
    }
  }
}

object DNBRepeatedWordsTest {
  def main(args: Array[String]): Unit = {
    val scanner = new Scanner(System.in)
    print("Exercise Length: (default = 5)")
    var exerciseLen = 5
    val exerciseLenInput = scanner.nextLine()
    if (exerciseLenInput.nonEmpty)
      exerciseLen = exerciseLenInput.toInt
    val dnbRepeatedWords = new DNBRepeatedWords(exerciseLen)
    dnbRepeatedWords.runGame()
  }
}
