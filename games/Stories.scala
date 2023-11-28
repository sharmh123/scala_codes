package TestCodesScala.games

import java.util.Scanner
import javax.swing.JOptionPane
import scala.io.Source
import scala.util.Random

class Stories(exerciseLen: Int) {
  val inputFile =
    "/Users/himash/testcodes/src/resources/DNB/stories/storyWords.txt"

  def run() = {
    while (true) {
      val exerciseWords = generateExerciseWords()
      var count = 1
      exerciseWords.foreach { word =>
        JOptionPane
          .showMessageDialog(null,
                             word,
                             "word: " + count,
                             JOptionPane.PLAIN_MESSAGE)
        count = count + 1
      }
      JOptionPane
        .showMessageDialog(null, "?", "", JOptionPane.PLAIN_MESSAGE)
      println(exerciseWords.reduce((x, y) => s"$x, $y"))
    }

  }

  /**
    * Generate the exercise words.
    *
    * @return List of exercise words.
    */
  def generateExerciseWords(): Seq[String] = {
    val words = Source
      .fromFile(inputFile)
      .getLines()
      .toList
      .flatMap(x => x.split("[\\,|\\.]\\s+"))
    val randomGen = new Random()
    var exerciseWords: Seq[String] = Seq[String]()
    (1 to exerciseLen).foreach { index =>
      val randomNumber = randomGen.nextInt(words.length)
      exerciseWords = exerciseWords :+ words(randomNumber)
    }
    exerciseWords
  }
}

object Stories {
  def main(args: Array[String]): Unit = {
    val scanner = new Scanner(System.in)
    println("ExerciseLen: ")
    val exerciseLen = scanner.nextInt()
    new Stories(exerciseLen).run()
  }
}
