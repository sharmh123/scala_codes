package TestCodesScala.games

import java.util.Scanner
import javax.swing.JOptionPane
import scala.collection.JavaConverters._
import scala.util.Random

trait DNBGame {
  def generateExerciseWords(): Seq[String]
  def runGame(): Unit
}

object LongWordConfigContainer {
  val reverseModeOn = "reverseModeOn"
  val numDigits = "numDigits"
}

class DNBLongNumbers(numDigits: Int, reverseMode: Boolean) {
  def runGame() = {
    while (true) {
      var random = getRandomNumber(numDigits)
      JOptionPane.showMessageDialog(null, random, "", JOptionPane.PLAIN_MESSAGE)
      JOptionPane.showMessageDialog(null, "", "", JOptionPane.PLAIN_MESSAGE)
      var randomToPrint = random.toString
      if (reverseMode) {
        randomToPrint = randomToPrint.reverse
      }
      println(randomToPrint)
    }
  }

  def getRandomNumber(numDigits: Int): Int = {

    val randomNumberGenerator = new Random()
    import math._
    val base = pow(10, numDigits - 1).toInt
    val max = (pow(10, numDigits) - 1).toInt
    val nextNumber = base + randomNumberGenerator.nextInt(max - base)
    return nextNumber
  }
}

object DNBLongWord {
  def main(args: Array[String]): Unit = {
    val scanner = new Scanner(System.in)
    var numDigits = 8
    print(s"NumDigits: (default = $numDigits)")

    val numDigitsInput = scanner.nextLine()
    if (!numDigitsInput.isEmpty) {
      numDigits = numDigitsInput.toInt
    }
    var reverseMode = true
    print(s"Reverse Mode: (default = $reverseMode)")
    val reverseModeInput = scanner.nextLine()
    if (!reverseModeInput.isEmpty) {
      reverseMode = reverseModeInput.toBoolean
    }
    val dnbLongNumbers = new DNBLongNumbers(numDigits, reverseMode)
    dnbLongNumbers.runGame()
  }
}
