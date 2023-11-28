package games

import javax.swing.JOptionPane
import scala.::
import scala.io.Source
import scala.util.Random

case class RestaurantOrder(guest: String, mainCourse: String, dessert: String, drink: String) {
  override def toString(): String = {
    s"$guest: [$mainCourse, $dessert, $drink]"
  }
}
class RestaurantOrderingLargeOrder(numGuests: Int) {
  val baseDir = "/Users/himanshusharma/IdeaProjects/resources/DNB/restaurantOrders/"
  val dessertsFile = baseDir + "desserts.csv"
  val mainCourseFile = baseDir + "mainCourse.csv"
  val drinksFile = baseDir + "drinks.csv"
  val namesFile = baseDir + "names.csv"

  def getItems(inputFile: String, numGuests: Int) = {
    val items = Source.fromFile(inputFile).getLines()
    val shuffledNames = Random.shuffle(items)
    shuffledNames.slice(0, numGuests).toList
  }

  def getOrders(): List[RestaurantOrder] = {
    val guests = getItems(namesFile, numGuests)
    val mainCourses = getItems(mainCourseFile, numGuests)
    val drinks = getItems(drinksFile, numGuests)
    val desserts = getItems(dessertsFile, numGuests)
    var orders = List[RestaurantOrder]()
    (0 until numGuests).foreach {
      index =>
        orders = orders :+ RestaurantOrder(guests(index), mainCourses(index), desserts(index), drinks(index))
    }
    return orders
  }

  def runGame(): Unit = {
    val orders = getOrders()
    orders.foreach {
      order => JOptionPane.showMessageDialog(null, s"$order", "",
        JOptionPane.PLAIN_MESSAGE)
    }
  }
}

object RestaurantOrderingLargeOrder {
  def main(args: Array[String]): Unit = {
    val orders = new RestaurantOrderingLargeOrder(4).runGame()
    while (true) {

    }
  }
}
