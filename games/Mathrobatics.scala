package TestCodesScala.games

import java.util.Scanner

import javax.swing.JOptionPane

import scala.util.Random

class Mathrobatics(nback: Int, maxThreshold: Int) {
  def showMessageDialog(message: String): Unit = {
    JOptionPane.showMessageDialog(null, message, "", JOptionPane.PLAIN_MESSAGE)
  }

  def runGame(): Unit = {
    var seedArray = Seq.fill(nback)(10 + Random.nextInt(20)).toArray
    seedArray.foreach(num =>
      showMessageDialog(num.toString))

    var counter = 0
    val operations = Array("+", "-")
    while (true) {
      val operation = operations(Random.nextInt(1))
      val secondOperand = 1 + Random.nextInt(15)
      operation match {
        case "+" =>
          if ((seedArray(counter) + secondOperand) > maxThreshold) {
            seedArray(counter) = seedArray(counter) - secondOperand
            showMessageDialog(s"-$secondOperand")
          }
          else {
            showMessageDialog(s"$operation$secondOperand")
            seedArray(counter) = seedArray(counter) + secondOperand
          }
        case "-" =>
          if ((seedArray(counter) - secondOperand) <= 0) {
            seedArray(counter) = seedArray(counter) + secondOperand
            showMessageDialog(s"+$secondOperand")
          }
          else {
            showMessageDialog(s"$operation$secondOperand")
            seedArray(counter) = seedArray(counter) - secondOperand
          }
      }
      print(s"counter: $counter, Expected: ${seedArray(counter)}\n")
      counter = (counter + 1) % nback
      if (counter == 0)
        println("==========================")
    }
  }
}

object Mathrobatics {
  def main(args: Array[String]): Unit = {
    val scanner: Scanner = new Scanner(System.in)
    var nback = 4
    print(s"n-back: (default = $nback)")
    val nbackInput = scanner.nextLine()
    if (nbackInput.nonEmpty)
      nback = nbackInput.toInt

    print("Max plus/minus: (default = 20)")
    var maxThreshold = 20
    val maxThresholdInput = scanner.nextLine()
    if (maxThresholdInput.nonEmpty)
      maxThreshold = maxThresholdInput.toInt

    val mathrobatics = new Mathrobatics(nback, maxThreshold)
    mathrobatics.runGame()
  }
}
