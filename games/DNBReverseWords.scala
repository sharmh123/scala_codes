package TestCodesScala.games

import java.util.Scanner
import javax.swing.JOptionPane
import TestcodesJava.games.DNBWordsJava
import scala.collection.JavaConverters._

class DNBReverseWords(exerciseLen: Int, reverseMode: Boolean) {
  def runGame(): Unit = {
    var index = 0
    while (true) {
      val words = generateExerciseWords(exerciseLen)
      words.foreach {
        word =>
          JOptionPane.showMessageDialog(null, word, (index + 1).toString, JOptionPane.PLAIN_MESSAGE)
          index = (index + 1) % (exerciseLen)
      }
      var message = "Repeat the words"
      if (reverseMode) {
        message = message + " in reverse order."
        JOptionPane.showMessageDialog(null, message, "", JOptionPane.PLAIN_MESSAGE)
        println(words.reverse.mkString(","))
      } else {
        message = message + "."
        JOptionPane.showMessageDialog(null, message, "", JOptionPane.PLAIN_MESSAGE)
        println(words.reverse.mkString(","))
      }
    }
  }

  def generateExerciseWords(exerciseLen: Int): Seq[String] =
    new DNBWordsJava().getExerciseWords(exerciseLen).asScala.toSeq
}

object DNBReverseWords {
  def main(args: Array[String]): Unit = {
    val scanner = new Scanner(System.in)
    var exerciseLen = 5
    print(s"Exercise Len: (default = $exerciseLen)")
    val exerciseLenInput = scanner.nextLine()
    if (!exerciseLenInput.isEmpty)
      exerciseLen = exerciseLenInput.toInt

    var reverseMode = true
    print(s"ReverseMode: (default = $reverseMode)")
    val reverseModeInput = scanner.nextLine()
    if (!reverseModeInput.isEmpty)
      reverseMode = reverseModeInput.toBoolean
    val dNBReverseWords = new DNBReverseWords(exerciseLen, reverseMode)
    dNBReverseWords.runGame()
  }
}
