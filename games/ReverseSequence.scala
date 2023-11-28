package TestCodesScala.games

import java.io.{File, PrintWriter}
import TestcodesJava.config.ConfigContainer
import TestcodesJava.core.DNBCore

import java.util.Scanner
import javax.swing.JOptionPane
import scala.collection.JavaConverters._
import scala.io.Source
import scala.util.Random

class ReverseSequence(exerciseLen: Int = 4) extends DNBGame {
  val baseDir = "/Users/himanshusharma/IdeaProjects/resources/DNB/config"
  val configFileName = baseDir + "/" + "reverseSequence_config.txt"
  val configContainer = new ConfigContainer(configFileName)

  /**
    * Generate the exercise words with repetitions.
    *
    * @return repeated words.
    */
  def generateRepeatedExerciseWords(): Seq[String] = {
    val inputFile = configContainer.getInputFileName
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

  /**
    * Generate the exercise words.
    * @return List of exercise words.
    */
  override def generateExerciseWords(): Seq[String] = {
    val inputFile = configContainer.getInputFileName
    val words = Source.fromFile(inputFile).getLines().toList
    val randomGen = new Random()
    var exerciseWords: Seq[String] = Seq[String]()
    (1 to exerciseLen).foreach {
      index =>
        val randomNumber = randomGen.nextInt(words.length)
        exerciseWords = exerciseWords :+ words(randomNumber)
    }
    exerciseWords
  }

  /**
    * reverse the input comparison file.
    */
  def reverseInputComparisonFile() = {
    val lines = Source.fromFile(configContainer.getInputComparisonFile).getLines().toSeq.reverse
    var fileTemp = new File(configContainer.getInputComparisonFile)
    fileTemp.delete()
    val printWriter = new PrintWriter(fileTemp)
    lines.foreach {
      line => printWriter.write(line + "\n")
    }
    printWriter.close()
  }

  /**
    *
    */
  override def runGame(): Unit = {
    while (true) {
      val words = generateExerciseWords()
      configContainer.setInputSentenceType(DNBConstants.InputSentenceTypeWord)
      configContainer.setNback(configContainer.getExerciseLen)
      DNBCore.startGameExecution(words.asJava, configContainer)
      JOptionPane.showMessageDialog(null,
        "Reverse the words", configContainer.getInputSentenceType + ": " + 0,
        JOptionPane.PLAIN_MESSAGE)
      words.reverse.foreach(x => print(x + " "))
      println()
    }
  }
}

object ReverseSequence {
  def main(args: Array[String]): Unit = {
    val scanner: Scanner = new Scanner(System.in)
    var exerciseLen = 5
    print("exercise Len: (default = 5) ")
    var exerciseLenStr: String = scanner.nextLine()
    if (exerciseLenStr.nonEmpty) {
      exerciseLen = exerciseLenStr.toInt
    }
    val reverseSequenceGame = new ReverseSequence(exerciseLen)
    reverseSequenceGame.runGame()
  }
}
