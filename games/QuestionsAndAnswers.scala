package TestCodesScala.games

import java.util.Scanner

import javax.swing.JOptionPane

import scala.io.Source

case class QuestionAndAnswer(question: String, answer: String)

class QuestionsAndAnswers(inputFile: String, startIndex: Int, endIndex: Int, numRepititions: Int) {
  val questionPrefix = "Q-"
  val answerPrefix = "A-"
  val baseDir = "/Users/himash/testcodes/src/Testcodes/src/resources/DNB/sentences"

  def readFile() = {
    val lines = Source.fromFile(s"$baseDir/$inputFile").getLines().toList
    var questionsAndAnswers = List[QuestionAndAnswer]()
    var count = startIndex
    while (count <= endIndex) {
      val questionExists = lines.filter(x => x.startsWith(s"$questionPrefix$count")).size == 1
      if (questionExists) {
        val question = lines.filter(x => x.startsWith(s"$questionPrefix$count"))(0)
        var answer = lines
          .filter(x => x.startsWith(s"$answerPrefix$count"))
          .flatMap(x => x.split("\\. "))
          .reduce((x, y) => x + "\n" + y)
          .replaceAll("(.{150})", "$1\n");

        questionsAndAnswers = questionsAndAnswers :+ QuestionAndAnswer(question, answer)
      }
      count += 1
    }
    questionsAndAnswers
  }

  def runGame() = {
    val questionsAndAnswers = readFile()
    (1 to numRepititions).foreach {
      index =>
        questionsAndAnswers.foreach {
          questionAndAnswer =>
            JOptionPane.showMessageDialog(null, questionAndAnswer.question, s"Question: $index",
              JOptionPane.PLAIN_MESSAGE)
            JOptionPane.showMessageDialog(null, questionAndAnswer.answer, s"Answer: $index",
              JOptionPane.PLAIN_MESSAGE)
            //println(questionAndAnswer.answer)
        }
    }
  }
}

object QuestionsAndAnswers {
  def main(args: Array[String]): Unit = {
    val scanner = new Scanner(System.in)
    println("Input File: ")
    val inputFile = scanner.next()
    println("startIndex: ")
    val startIndex = scanner.nextInt()
    println("endIndex: ")
    val endIndex = scanner.nextInt()
    println("Number of repititions: ")
    val numRepititions = scanner.nextInt()
    new QuestionsAndAnswers(inputFile, startIndex, endIndex, numRepititions).runGame()
  }
}
