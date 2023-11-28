package TestCodesScala.games

import java.util.Scanner

import javax.swing.JOptionPane

import scala.io.Source
import scala.util.Random

case class RestaurantOrders(numGuests: Int, gameMode: String, numItems: Int = 1) {

  import RestaurantOrders._

  val baseDir = "/Users/himanshusharma/IdeaProjects/resources/DNB/restaurantOrders"
  val dessertsFile = "desserts.csv"
  val mainCourseFile = "mainCourse.csv"
  val drinksFile = "drinks.csv"
  val startersFile = "starters.csv"
  val namesFile = "names.csv"

  case class Order(guest: String, item: String) {
    override def toString(): String = {
      return s"$guest: [$item]"
    }
  }

  def getItems(fileName: String): List[String] = {
    val inputFileName = s"$baseDir/$fileName"
    val names = Source.fromFile(inputFileName).getLines()
    val shuffledNames = Random.shuffle(names)
    shuffledNames.slice(0, numGuests).toList
  }

  def getOrders(): List[Order] = {
    val guestNames = getItems(namesFile)
    val starters = getItems(startersFile)
    val mainCourseItems = getItems(mainCourseFile)
    val drinks = getItems(drinksFile)
    val desserts = getItems(dessertsFile)
    val allItems = Random.shuffle(starters ++ mainCourseItems ++ drinks ++ desserts)
      .map(item => item.split(" ").take(2).mkString(" "))
    var orders = List[Order]()
    (0 to (guestNames.size - 1)).foreach {
      i => orders = orders :+ Order(guestNames(i), allItems(i))
    }
    orders
  }

  def runListingMode(orders: List[Order]): Int = {
    orders.foreach {
      order =>
        JOptionPane.showMessageDialog(null, s"$order", "", JOptionPane.PLAIN_MESSAGE)
    }

    val options: Array[AnyRef] = Array("OK", "Restart")
    while (true) {
      val shuffledOrders = Random.shuffle(orders)
      shuffledOrders.foreach {
        order =>
          val result = JOptionPane.showOptionDialog(null, order.guest + "?", "",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            options, options(0))
          println(order)
          if (result == JOptionPane.NO_OPTION) {
            println("============")
            runGame()
          }
      }
      println("============")
    }
    return 0
  }

  def runMatchingMode(orders: List[Order]) = {
    orders.foreach {
      order =>
        JOptionPane.showMessageDialog(null, s"$order", "", JOptionPane.PLAIN_MESSAGE)
    }

    while (true) {
      val allItems = orders.map(_.item)
      while (true) {
        val randomItem = allItems(Random.nextInt(allItems.size))
        JOptionPane.showMessageDialog(null, randomItem + "?", "", JOptionPane.PLAIN_MESSAGE)
        val correspondingOrder = orders.filter(order => (order.item == randomItem)
        )
        println("Guest: " + correspondingOrder(0).guest)
      }
    }
  }

  def runGame(): Int = {
    val orders = getOrders()
    gameMode match {
      case RestaurantOrders.gameMode_Listing =>
        runListingMode(orders)
      case RestaurantOrders.gameMode_Matching =>
        runMatchingMode(orders)
      case _ => throw new Exception("unexpected mode..")
    }
    return 0
  }
}

object RestaurantOrders {
  val gameMode_Matching = "matching"
  val gameMode_Listing = "listing"

  def main(args: Array[String]): Unit = {
    val scanner = new Scanner(System.in)
    var numGuests = 5
    print(s"Num Guests: (default=$numGuests)")
    val numGuestsInput = scanner.nextLine()
    if (!numGuestsInput.isEmpty)
      numGuests = numGuestsInput.toInt

    new RestaurantOrders(numGuests, gameMode_Listing).runGame()
  }
}
