package TestCodesScala.games

import java.util.Scanner

import javax.swing.JOptionPane

import scala.util.Random

class ChainReaction(maxThreshold: Int, maxOffset: Int) {
  def getMappings(): Map[String, Int] = {
    val items = Seq("dog", "cow", "bat", "tiger", "clock", "sofa", "bottle")
    val mappings = items.map(item => (item -> Random.nextInt(items.size))).toMap
    mappings
  }
  def runGame(): Unit = {
    val seed = Random.nextInt(20)
    val operations = Array("+", "-")
    var currentValue = seed
    JOptionPane.showMessageDialog(null, currentValue, "", JOptionPane.PLAIN_MESSAGE)
    var count = 1
    while (true) {
      val random = Random.nextInt(10)
      val index = if (random < 5) 0 else 1
      val operation = operations(index)
      val operand = 1 + Random.nextInt(maxOffset - 1)
      operation match {
        case "+" =>
          if ((currentValue + operand) > maxThreshold) {
            JOptionPane.showMessageDialog(null, s"-$operand", count.toString, JOptionPane.PLAIN_MESSAGE)
            currentValue = currentValue - operand
          }
          else {
            JOptionPane.showMessageDialog(null, s"$operation$operand", count.toString, JOptionPane.PLAIN_MESSAGE)
            currentValue = currentValue + operand
          }
        case "-" =>
          if ((currentValue - operand) <= 0) {
            JOptionPane.showMessageDialog(null, s"+$operand", count.toString, JOptionPane.PLAIN_MESSAGE)
            currentValue = currentValue + operand
          }
          else {
            JOptionPane.showMessageDialog(null, s"$operation$operand", count.toString, JOptionPane.PLAIN_MESSAGE)
            currentValue = currentValue - operand
          }
      }
      count = count + 1
      println(s"$count) Expected: $currentValue")
    }
  }
}

object ChainReaction {
  def main(args: Array[String]): Unit = {
    val scanner = new Scanner(System.in)
    var maxOffset = 20
    print(s"Max plus/minus (default = $maxOffset): ")
    val maxOffsetInput = scanner.nextLine()
    if (maxOffsetInput.nonEmpty) {
      maxOffset = maxOffsetInput.toInt
    }
    var maxThreshold = 50
    print(s"Upper Bound on numbers (default = $maxThreshold): ")
    val maxThresholdInput = scanner.nextLine()
    if (maxThresholdInput.nonEmpty) {
      maxThreshold = maxThresholdInput.trim.toInt
    }
    val chainReaction = new ChainReaction(maxThreshold, maxOffset)
    chainReaction.runGame()
  }
}
