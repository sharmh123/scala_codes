package TestCodesScala.games
import java.util.Scanner

import TestcodesJava.config.ConfigContainer
import TestcodesJava.core.DNBCore
import javax.swing.JOptionPane

import scala.io.Source
import scala.util.Random
import scala.collection.JavaConverters._

class RepeatedNumbers(maxThreshold: Int) {
  def runGame(): Unit = {
    while (true) {
      val word = Random.nextInt(maxThreshold)
      JOptionPane.showMessageDialog(null, word.toString, "", JOptionPane.PLAIN_MESSAGE)
      println(s"Expected: $word")
    }
  }
}

object RepeatedNumbers {
  def main(args: Array[String]): Unit = {
    val scanner: Scanner = new Scanner(System.in)
    var maxThreshold = 15
    print(s"max number in the list: (default = $maxThreshold)")
    val maxThresholdInput = scanner.nextLine()
    if (!maxThresholdInput.isEmpty)
      maxThreshold = maxThresholdInput.toInt
    val dnbRepeatedWords = new RepeatedNumbers(maxThreshold)
    dnbRepeatedWords.runGame()
  }
}

