package games

import java.util.Scanner
import javax.swing.JOptionPane
import scala.io.Source
import scala.util.Random

class QuickSwitch(numObjects: Int, numColors: Int, numPlaces: Int) {
  val baseDir = "/Users/himanshusharma/IdeaProjects/resources/DNB/quickSwitch/"
  val objectsFile = baseDir + "objects.csv"
  val colorsFile = baseDir + "colors.csv"
  val placesFile = baseDir + "places.csv"

  def getItems(fileName: String, numItems: Int) = {
    val items = Source.fromFile(fileName).getLines().toList
    Random.shuffle(items).take(numItems)
  }

  def runGame() = {
    val objects = getItems(objectsFile, numObjects)
    val colors = getItems(colorsFile, numColors)

    println("===============")
    println("Objects: [" + objects.mkString(", ") + "]")
    println("Colors: [" + colors.mkString(", ") + "]")

    var places = List[String]()
    if (numPlaces > 0) {
      places = getItems(placesFile, numPlaces)
      println("Places: [" + places.mkString(", ") + "]")
    }
    println("===============")

    var objIndex = 0
    var colorIndex = 0
    var loopIndex = 0
    var placesIndex = 0
    JOptionPane.showMessageDialog(null, "Next?",
            "", JOptionPane.PLAIN_MESSAGE)
    while (true) {
      loopIndex match {
        case 0 =>
          JOptionPane.showMessageDialog(null, objects(objIndex),
            "", JOptionPane.PLAIN_MESSAGE)
          objIndex = (objIndex + 1) % numObjects
        case 1 =>
          JOptionPane.showMessageDialog(null, colors(colorIndex),
            "", JOptionPane.PLAIN_MESSAGE)
          colorIndex = (colorIndex + 1) % numColors
        case 2 =>
          JOptionPane.showMessageDialog(null, places(placesIndex),
            "", JOptionPane.PLAIN_MESSAGE)
          placesIndex = (placesIndex + 1) % numPlaces
        case _ => throw new Exception(s"Wrong loopIndex: $loopIndex")
      }
      val modN = if (numPlaces > 0) 3 else 2
      loopIndex = (loopIndex + 1) % modN
    }
  }
}

object QuickSwitch {
  def main(args: Array[String]): Unit = {
    val scanner = new Scanner(System.in)

    var numObjects = 4
    print(s"NumObjects: (default=$numObjects)")
    val numObjectsInput = scanner.nextLine()
    if (numObjectsInput.nonEmpty)
      numObjects = numObjectsInput.trim.toInt

    var numColors = 3
    print(s"NumColors: (default=$numColors)")
    val numColorsInput = scanner.nextLine()
    if (numColorsInput.nonEmpty)
      numColors = numColorsInput.trim.toInt

    var numPlaces = 0
    print(s"NumPlaces: (default=$numPlaces)")
    val numPlacesInput = scanner.nextLine()
    if (numPlacesInput.nonEmpty)
      numPlaces = numPlacesInput.trim.toInt

    new QuickSwitch(numObjects, numColors, numPlaces).runGame()
  }
}
