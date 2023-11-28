package TestCodesScala.games

import java.util.Scanner

import javax.swing.JOptionPane

import scala.io.Source
import scala.util.Random

import scala.collection.JavaConverters._

class Cities(exerciseLength: Int, numTours: Int) {
  val baseDir = "/Users/himanshusharma/IdeaProjects/resources/DNB/cities"
  val citiesFile = "cities.csv"
  val namesFile = "names.csv"

  def getCities(exerciseLen: Int): List[String] = {
    val fileName = s"$baseDir/$citiesFile"
    val cities = Source.fromFile(fileName).getLines().toList.map(x => x.split(",")(0))
    val shuffledCities = Random.shuffle(cities)

    shuffledCities.slice(0, exerciseLen)
  }

  def getNames(numTours: Int): List[String] = {
    val fileName = s"$baseDir/$namesFile"
    val names = Source.fromFile(fileName).getLines()
    val shuffledNames = Random.shuffle(names)
    shuffledNames.slice(0, numTours).toList
  }

  def runGame(): Int = {
    val names = getNames(numTours)
    val options: Array[AnyRef] = Array("OK", "Restart")
    var count = 1
    var nameCitiesMapping = Map[String, String]()
    names.foreach {
      name =>
        val cities = getCities(exerciseLength)
        val citiesStr = "[ " + String.join(" -> ", cities.asJava) + " ]"
        nameCitiesMapping += (name -> citiesStr)
        val message = s"$name: $citiesStr"
        JOptionPane.showMessageDialog(null, message, count.toString, JOptionPane.PLAIN_MESSAGE)
        count += 1
    }
    while (true) {
      println("==================")
      val shuffledNames = Random.shuffle(names)
      shuffledNames.foreach {
        name =>
          val result = JOptionPane.showOptionDialog(null,
            name + "?",
            "",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            options,
            options(0))
          if (result == JOptionPane.NO_OPTION) {
            runGame()
          }
          println(s"$name: " + nameCitiesMapping(name))
      }
    }
    return 0
  }
}

object Cities {
  def main(args: Array[String]): Unit = {
    val scanner = new Scanner(System.in)
    var numTours = 5
    print(s"Num Tours: (default = $numTours)")
    val numToursInput = scanner.nextLine()
    if (!numToursInput.isEmpty)
      numTours = numToursInput.toInt

    var numCitiesPerTour = 1
    print(s"Num Cities per Tour: (default = $numCitiesPerTour)")
    val numCitiesPerTourInput = scanner.nextLine()
    if (!numCitiesPerTourInput.isEmpty)
      numCitiesPerTour = numCitiesPerTourInput.toInt

    val cities = new Cities(numCitiesPerTour, numTours)
    cities.runGame()
  }
}
