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
class BetterMath(maxThreshold: Int, numTerms: Int) {
  val operatorsList = Array("+", "-", "*")

  def getExpression(): String = {
    val operators = getOperator()
    val operands = (1 to numTerms).map(_ => 1 + Random.nextInt(maxThreshold))
    var expression = ""
    (0 to operators.size - 1).foreach {
      i =>
        expression = expression + operands(i) + " " + operators(i) + " "
    }
    expression = expression + operands(operands.size - 1) + "?"
    expression
  }

  def getOperator() = {
    (1 to (numTerms - 1)).map(_ => operatorsList(Random.nextInt(3)))
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
    var counter = 1
    val scanner = new Scanner(System.in)
    while (true) {
      val expression = getExpression()
      val answer = evaluateExpression(expression)
      if ((-1000 <= answer) && (answer <= 1000)) {
        JOptionPane.showMessageDialog(null, expression, counter.toString, JOptionPane.PLAIN_MESSAGE)
        println(s"$counter) $expression = $answer")
        counter += 1
      }
    }
  }
}

object BetterMath {
  def main(args: Array[String]): Unit = {
    val scanner = new Scanner(System.in)
    var maxThreshold = 20
    print(s"Upper bound on size of numbers: (default = $maxThreshold)")
    val maxThresholdInput = scanner.nextLine()
    if (!maxThresholdInput.isEmpty)
      maxThreshold = maxThresholdInput.toInt

    var numTerms = 3
    print("number of terms: (default = 3)")
    val numTermsInput = scanner.nextLine()
    if (!numTermsInput.isEmpty)
      numTerms = numTermsInput.toInt
    new BetterMath(maxThreshold, numTerms).runGame()
  }
}
