package TestCodesScala.games

import java.util.{Collections, Scanner}
import TestcodesJava.config.ConfigContainer
import TestcodesJava.core.DNBCore
import TestcodesJava.games.DNBWords

import scala.util.Random
import scala.collection.JavaConverters._

class DNBJumbleWords {
  val baseDir = "/Users/himanshusharma/IdeaProjects/resources/DNB/config"
  val jumbledWordsConfigFileName: String = baseDir + "/" + "jumbleWords_config.txt"
  val jumbledWordsConfigContainer = new ConfigContainer(jumbledWordsConfigFileName)

  def runGame(exerciseLen: Int): Unit = {
    val words: java.util.List[String] = new DNBWords().getExerciseWords(exerciseLen)

    //DNBCore.runActualGameExecution(words, jumbledWordsConfigContainer)
    DNBCore.startGameExecution(words, jumbledWordsConfigContainer)
//    DNBCore.displayResults(jumbledWordsConfigContainer)
  }
}

object DNBJumbleWords {
  def shuffleWord(word: String): String = {
    val charArray = word.toCharArray
    val random = new Random()

    for(i <- 0 to (charArray.length - 1))
    {
      val j = random.nextInt(charArray.length - 1);
      val temp = charArray(i);
      charArray(i) = charArray(j);
      charArray(j) = temp;
    }

    new String(charArray)
  }

  def main(args: Array[String]): Unit = {
    val dnbJumbleWords = new DNBJumbleWords()
    val scanner = new Scanner(System.in)
    println("Exercise Len: ")
    val exerciseLen = scanner.nextInt()
    dnbJumbleWords.runGame(exerciseLen)
  }
}
