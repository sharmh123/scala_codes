package TestCodesScala.games

import java.util.Scanner
import javax.swing.JOptionPane
import scala.io.Source
import scala.util.Random

class RandomSpeechTopic(mode: Int) extends DNBGame {
  val baseDir = s"/Users/himanshusharma/IdeaProjects/resources/DNB/random_topics"
  val randomTopicsFile = s"$baseDir/random_topics"
  val interviewQuestionsFile = s"$baseDir/interview_questions"

  override def runGame(): Unit = {
    val topics = generateExerciseWords()
    val randomTopics = Random.shuffle(topics)
    val options: Array[AnyRef] = Array("OK", "Restart")
    randomTopics.foreach { topic =>
      JOptionPane.showOptionDialog(null,
                                   s"$topic",
                                   "",
                                   JOptionPane.YES_NO_OPTION,
                                   JOptionPane.PLAIN_MESSAGE,
                                   null,
                                   options,
                                   options(0))
    }
  }

  override def generateExerciseWords(): Seq[String] = {
    val inputFile = mode match {
      case 2 => interviewQuestionsFile
      case _ => randomTopicsFile
    }
    val topics = Source.fromFile(inputFile).getLines().toList
    topics
  }
}

object RandomSpeechTopic {
  def main(args: Array[String]): Unit = {
    val scanner = new Scanner(System.in)
    println("Mode: 1) Random, 2) Itv. Default = 1")
    val mode = scanner.nextLine().trim match {
      case "2" => 2
      case _   => 1
    }
    new RandomSpeechTopic(mode).runGame()
  }
}
