package TestCodesScala.games

import java.io.File

import scala.io.Source

object ReadingSpeed {
  def main(args: Array[String]): Unit = {
    val fileName = "/Users/himash/test_reading"
    val lines = readFile(fileName);
    val wordCount = lines.map(line => line.split("\\s+").size).sum
    println(s"wordcount: $wordCount")
  }

  def readFile(fileName: String): Seq[String] = {
     Source.fromFile(new File(fileName)).getLines().toSeq
  }
}
