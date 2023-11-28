package games

import javax.swing.JOptionPane
import scala.util.Random

case class BrainShiftTuple(letter: String,
                           number: Int,
                           color: String,
                           substance: String
                          ) {
  override def toString: String = s"[ $letter, $number, $color, $substance ]"
}

class BrainShiftOverdrive {
  val liquid = "liquid"
  val solid = "solid"
  val gas = "gas"

  val letters = Map("A" -> true, "B" -> false, "C" -> false, "D" -> false,
    "E" -> true, "F" -> false, "G" -> false, "H" -> false, "I" -> true,
    "J" -> false, "K" -> false, "L" -> false, "O" -> true, "U" -> true
  )
  val numbers = Map(0 -> true, 1 -> false, 2 -> true, 3 -> false,
    4 -> true, 5 -> false, 6 -> true, 7 -> false, 8 -> true, 9 -> false
  )
  val colors = Map("Violet" -> true, "cyan" -> false, "indigo" -> true, "blue" -> true,
    "magenta" -> false, "turquoise" -> false, "green" -> true, "yellow" -> true,
    "peach" -> false, "beige" -> false, "orange" -> true, "red" -> true, "white" -> false,
    "black" -> false, "brown" -> false
  )
  val substances = Map("water" -> liquid, "milk" -> liquid, "phone" -> solid,
    "nitrogen" -> gas, "hydrogen" -> gas, "oxygen" -> gas, "laptop" -> solid,
    "table" -> solid, "sofa" -> solid, "oil" -> liquid, "air" -> gas, "smoke" -> gas
  )

  val numberIsEven = "NumberIsEven"
  val numberIsOdd = "numberIsOdd"
  private val numberNotEven = "numberNotEven"
  private val numberNotOdd = "numberNotOdd"
  private val numberIsDivisibleBy3 = "numberIsDivisibleBy3"
  private val numberIsGreaterThan5 = "numberIsGreaterThan5"
  private val numberIsNotDivisibleBy3 = "numberIsNotDivisibleBy3"
  private val numberIsNotGreaterThan5 = "numberIsNotGreaterThan5"

  private val letterIsVowel = "letterIsVowel"
  private val letterIsConsonant = "letterIsConsonant"
  private val letterNotVowel = "letterNotVowel"
  private val letterNotConsonant = "letterNotConsonant"

  private val colorInRainbow = "colorInRainbow"
  private val colorNotInRainbow = "colorNotInRainbow"

  private val substanceIsSolid = "substanceIsSolid"
  private val substanceIsLiquid = "substanceIsLiquid"
  private val substanceIsGas = "substanceIsGas"
  private val substanceIsLiquidOrGas = "substanceIsLiquidOrGas"
  private val substanceIsNotLiquidOrGas = "substanceIsNotLiquidOrGas"
  private val substanceIsNotLiquid = "substanceIsNotLiquid"
  private val substanceIsNotSolid = "substanceIsNotSolid"
  private val letterComesAfterM = "letterComesAfterM"
  private val letterDoesNotComeAfterM = "letterDoesNotComeAfterM"

  val questions = Map(
    "The number is even" -> numberIsEven,
    "The number is odd" -> numberIsOdd,
    "The number is divisible by 3" -> numberIsDivisibleBy3,
    "The number is greater than 5" -> numberIsGreaterThan5,
    "The number is not divisible by 3" -> numberIsNotDivisibleBy3,
    "The number is not greater than 5" -> numberIsNotGreaterThan5,

    "The letter is a vowel" -> letterIsVowel,
    "The letter is a consonant" -> letterIsConsonant,
    "The color exists in a rainbow" -> colorInRainbow,
    "The substance is a solid" -> substanceIsSolid,
    "The substance is a liquid" -> substanceIsLiquid,
    "The substance is a gas" -> substanceIsGas,
    "The substance is a liquid or gas" -> substanceIsLiquidOrGas,
    "The substance is not a liquid or gas" -> substanceIsNotLiquidOrGas,
    "The substance is not a liquid" -> substanceIsNotLiquid,
    "The substance is not a solid" -> substanceIsNotSolid,
    "The color does not exist in a rainbow" -> colorNotInRainbow,
    "The number is not even" -> numberNotEven,
    "The number is not odd" -> numberNotOdd,
    "The letter is not a vowel" -> letterNotVowel,
    "The letter is not a consonant" -> letterNotConsonant,
    "The letter comes after M" -> letterComesAfterM,
    "The letter does not come after M" -> letterDoesNotComeAfterM,
  )

  def evaluateQuestion(questionStr: String, tuple: BrainShiftTuple): Boolean = {
    questionStr match {
      case this.numberIsEven => isNumberEven(tuple)
      case this.numberIsOdd => isNumberOdd(tuple)
      case this.numberNotEven => !isNumberEven(tuple)
      case this.numberNotOdd => !isNumberOdd(tuple)
      case this.numberIsGreaterThan5 => isnumberGreaterThan5(tuple)
      case this.numberIsDivisibleBy3 => isnumberDivisibleBy3(tuple)
      case this.numberIsNotGreaterThan5 => !isnumberGreaterThan5(tuple)
      case this.numberIsNotDivisibleBy3 => !isnumberDivisibleBy3(tuple)

      case this.letterIsVowel => isLetterVowel(tuple)
      case this.letterIsConsonant => isLetterConsonant(tuple)
      case this.letterNotVowel => !isLetterVowel(tuple)
      case this.letterNotConsonant => !isLetterConsonant(tuple)
      case this.letterComesAfterM => letterComesAfterM(tuple)
      case this.letterDoesNotComeAfterM => !letterComesAfterM(tuple)

      case this.colorInRainbow => isRainbowColor(tuple)
      case this.colorNotInRainbow => !isRainbowColor(tuple)

      case this.substanceIsSolid => isSubstanceSolid(tuple)
      case this.substanceIsLiquid => isSubstanceLiquid(tuple)
      case this.substanceIsGas => isSubstanceGas(tuple)
      case this.substanceIsLiquidOrGas => isSubstanceLiquid(tuple) || isSubstanceGas(tuple)
      case this.substanceIsNotLiquidOrGas => !(isSubstanceLiquid(tuple) || isSubstanceGas(tuple))
      case this.substanceIsNotLiquid => !isSubstanceLiquid(tuple)
      case this.substanceIsNotSolid => !isSubstanceSolid(tuple)

      case _ => throw new Exception(s"Illegal question number: $questionStr")
    }
  }

  def letterComesAfterM(tuple: BrainShiftTuple): Boolean = {
    val alphabet = "abcdefghijklmnopqrstuvwxyz".toUpperCase()
    alphabet.indexOf(tuple.letter) > 12
  }

  def isnumberGreaterThan5(tuple: BrainShiftTuple): Boolean = {
    tuple.number > 5
  }

  def isnumberDivisibleBy3(tuple: BrainShiftTuple): Boolean = {
    tuple.number %3 == 0
  }

  def isNumberEven(tuple: BrainShiftTuple): Boolean = {
    tuple.number % 2 == 0
  }

  def isNumberOdd(tuple: BrainShiftTuple): Boolean = {
    tuple.number % 2 != 0
  }

  def isLetterVowel(tuple: BrainShiftTuple): Boolean = {
    letters(tuple.letter)
  }

  def isLetterConsonant(tuple: BrainShiftTuple): Boolean = {
    !letters(tuple.letter)
  }

  def isRainbowColor(tuple: BrainShiftTuple): Boolean = {
    colors(tuple.color)
  }

  def isSubstanceSolid(tuple: BrainShiftTuple): Boolean = {
    substances(tuple.substance) == solid
  }

  def isSubstanceLiquid(tuple: BrainShiftTuple): Boolean = {
    substances(tuple.substance) == liquid
  }

  def isSubstanceGas(tuple: BrainShiftTuple): Boolean = {
    substances(tuple.substance) == gas
  }

  def getRandomLetter(): String = {
    val randomIndex = Random.nextInt(letters.size - 1)
    letters.toList(randomIndex)._1
  }

  def getRandomNumber(): Int = {
    val randomIndex = Random.nextInt(numbers.size - 1)
    numbers.toList(randomIndex)._1
  }

  def getRandomColor(): String = {
    val randomIndex = Random.nextInt(colors.size - 1)
    colors.toList(randomIndex)._1
  }

  def getRandomSubstance(): String = {
    val randomIndex = Random.nextInt(substances.size - 1)
    substances.toList(randomIndex)._1
  }

  def getRandomQuestion(): String = {
    val randomIndex = Random.nextInt(questions.size - 1)
    questions.toList(randomIndex)._1
  }

  def getRandomTuple() = {
    val letter = getRandomLetter()
    val number = getRandomNumber()
    val color = getRandomColor()
    val substance = getRandomSubstance()
    BrainShiftTuple(letter, number, color, substance)
  }

  def runGame() = {
    var count = 1
    while(true) {
      val question = getRandomQuestion()
      val randomTuple = getRandomTuple()
      JOptionPane.showMessageDialog(null,
        s"$question\n\n$randomTuple", "", JOptionPane.PLAIN_MESSAGE
      )
      val outcome = evaluateQuestion(questions(question), randomTuple)
      println(s"$count) $randomTuple : $question :: $outcome")
      count = count + 1
    }
  }
}

object BrainShiftOverdrive {
  def main(args: Array[String]): Unit = {
    new BrainShiftOverdrive().runGame()
  }
}
