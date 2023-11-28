package TestCodesScala.games

import java.util.Scanner

import javax.swing.JOptionPane

import scala.collection.mutable
import scala.util.Random

/**
 * 1 + 2 + 3
 * 1 + 2 * 3
 * 1 * 2 + 3
 * 12 * 212 * 30 + 10
 * 112 * 122 - 3123
 * 1 - 2 * 3
 *
 * @param maxThreshold
 * @param numTerms
 */
class NBackSum(maxThreshold: Int, nBack: Int) {

  def getNextNumber(maxThreshold: Int): Int = {
    Random.nextInt(maxThreshold);
  }

  def precedence(operator: Char): Int = {
    val result = operator match {
      case '+' | '-' => 1
      case '*' => 2
      case _ => 0
    }
    result
  }

  def applyOperator(operator: Char, operand1: Int, operand2: Int): Int = {
    val result = operator match {
      case '+' => operand1 + operand2
      case '-' => operand1 - operand2
      case '*' => operand1 * operand2
      case _ => throw new Exception("Unexpected Operator")
    }
    result
  }

  def evaluateExpression(expression: String): Int = {
    val operandStack = new mutable.Stack[Int]()
    val operatorStack = new mutable.Stack[Char]()
    var i = 0
    while (i < expression.length) {
      if (expression(i) != ' ') {
        if (expression(i).isDigit) {
          var num = 0
          while (i < expression.length && expression(i).isDigit) {
            num = num * 10 + expression(i).toString.toInt
            i += 1
          }
          operandStack.push(num)
        }
        else {
          while ((!operatorStack.isEmpty) && (precedence(operatorStack.top) >= precedence(expression(i)))) {
            val operand2 = operandStack.pop()
            val operand1 = operandStack.pop()
            val operator = operatorStack.pop()
            val result = applyOperator(operator, operand1, operand2)
            operandStack.push(result)
          }
          operatorStack.push(expression(i))
        }
      }
      i += 1
    }

    while (!operatorStack.isEmpty) {
      val operator = operatorStack.pop()
      val operand2 = operandStack.pop()
      val operand1 = operandStack.pop()
      operandStack.push(applyOperator(operator, operand1, operand2))
    }

    return operandStack.top
  }

  def runGame() = {
    var array = Array.fill(nBack)(0)
    var counter = 1
    while (true) {
      val nextNumber = getNextNumber(maxThreshold)
      if (counter > nBack) {
        for (i <- 1 to nBack - 1) {
          array(i - 1) = array(i)
        }
        array(nBack - 1) = nextNumber
      } else {
        array(counter - 1) = nextNumber
      }

      JOptionPane.showMessageDialog(null, nextNumber, counter.toString, JOptionPane.PLAIN_MESSAGE)
      if (counter >= nBack) {
        val answer = array.sum
        println(s"$counter) prevNumber: $nextNumber Answer = $answer")
      } else {
        println(s"$counter) prevNumber: $nextNumber")
      }
      counter += 1
    }
  }
}

object NBackSum {
  def main(args: Array[String]): Unit = {
    val scanner = new Scanner(System.in)
    var maxThreshold = 20
    print(s"Upper bound on size of numbers: (default=$maxThreshold)")
    val maxThresholdInput = scanner.nextLine()
    if (!maxThresholdInput.isEmpty) {
      maxThreshold = maxThresholdInput.toInt
    }
    var nBack = 3
    print(s"n-back: (default=$nBack)")
    val nBackInput = scanner.nextLine()
    if (!nBackInput.isEmpty)
      nBack = nBackInput.toInt
    new NBackSum(maxThreshold, nBack).runGame()
  }
}
