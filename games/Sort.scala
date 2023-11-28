package TestCodesScala.games

import java.util.Scanner
import javax.swing.JOptionPane
import scala.util.Random

class Sort(nums: Int) extends DNBGame {
  override def runGame(): Unit = {
    while (true) {
      val words = generateExerciseWords()
      val wordStrings = words.mkString(", ")
      JOptionPane.showMessageDialog(null,
                                    wordStrings,
                                    "word: ",
                                    JOptionPane.PLAIN_MESSAGE)
      println(words.map(x => x.toInt).sorted.mkString(", "))
    }
  }

  override def generateExerciseWords(): Seq[String] = {
    val integers = (1 to nums).map { _ =>
      val randomInt = -100 + Random.nextInt(200)
      randomInt.toString
    }
    integers
  }
}

object Sort {
  def main(args: Array[String]): Unit = {
    val scanner = new Scanner(System.in);
    println("Sequence Length: ")
    val numWords = scanner.nextInt()
    val sortGame = new Sort(numWords)
    sortGame.runGame()
  }
}
