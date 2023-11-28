package TestCodesScala.games

import java.util.Scanner

import javax.swing.JOptionPane

import scala.io.Source
import scala.util.Random

class SymbolMappings(maxCardinality: Int, timed: Boolean) {

  val baseDir = "/Users/himanshusharma/IdeaProjects/resources/DNB/symbolMappings/"
  def getColors(): Array[String] = {
    val colorsFile = baseDir + "colors.csv"
    val colors = Source.fromFile(colorsFile).getLines().toList
    colors.toArray
  }

  def getPlaces(): Array[String] = {
    val placesFile = baseDir + "places.csv"
    val places = Source.fromFile(placesFile).getLines().toList
    places.toArray
  }

  val colors = getColors()
  val places = getPlaces()
  val symbols = (('A' to 'Z').map(_.toString) ++ (1 to 20).map(_.toString)).toArray ++
    colors ++ places
  val filePath =
    "/Users/himanshusharma/IdeaProjects/resources/DNB/words/CommonWordsOnly.txt"

  def runGame(): Int = {
    val words = Source.fromFile(filePath).getLines().toArray
    println("===============")
    val options: Array[AnyRef] = Array("OK", "Restart")
    var exercisePairs = Seq[(String, String)]()
    (1 to maxCardinality).foreach { _ =>
      val symbol = symbols(Random.nextInt(symbols.size - 1))
      exercisePairs = exercisePairs :+ (words(Random.nextInt(words.size)), symbol)
    }
    var count = 1
    exercisePairs.foreach { pair =>
      JOptionPane.showMessageDialog(null,
        "(" + pair._1 + ", " + pair._2 + ")",
        "word: " + count,
        JOptionPane.PLAIN_MESSAGE)
      count = count + 1
    }
    while (true) {
      val shuffledPairs = Random.shuffle(exercisePairs)
      println("===============")
      shuffledPairs.foreach { shuffledPair =>
        val result = JOptionPane.showOptionDialog(null,
          shuffledPair._1 + "?",
          "",
          JOptionPane.YES_NO_OPTION,
          JOptionPane.PLAIN_MESSAGE,
          null,
          options,
          options(0))
        if (result == JOptionPane.NO_OPTION) {
          runGame()
        }
        println(s"Expected: ${shuffledPair._2}")
      }
    }
    return 0
  }
}

object SymbolMappings {
  def main(args: Array[String]): Unit = {
    val scanner = new Scanner(System.in)
    var maxCardinality = 6
    print(s"Number of Symbols to use: (default = $maxCardinality)")
    val maxCardinalityInput = scanner.nextLine()
    if (!maxCardinalityInput.isEmpty) {
      maxCardinality = maxCardinalityInput.toInt
    }
    val symbolMappings = new SymbolMappings(maxCardinality, false)
    symbolMappings.runGame()
  }
}
