package TestCodesScala.games

import java.util.Scanner
import javax.swing.JOptionPane
import scala.io.Source
import scala.util.Random

class WordMeaningsRandom(exerciseLen: Int,
                         learningMode: Boolean,
                         printSentences: Boolean,
                         showMeaningAndAskWord: Boolean) {
  val fileName =
    "/Users/himanshusharma/IdeaProjects/resources/DNB/wordmeanings/top1000Words.csv"

  def runGame(): Int = {
    val lines =
      Source.fromFile(fileName).getLines().map(x => x.split("->")).toList
    val startIndex = 0 + Random.nextInt(lines.size)
    //Random.between(0, lines.size)
    val endIndex =
      if ((startIndex + exerciseLen) > lines.size - 1) lines.size - 1
      else startIndex + exerciseLen
    val wordMeanings = lines.slice(startIndex, endIndex)
    val options: Array[AnyRef] = Array("OK", "Restart")
    var count = startIndex
    wordMeanings.foreach {
      wordMeaning =>
        val result  = JOptionPane.showOptionDialog(null, wordMeaning(0),
      "", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options(0))
        if (result == JOptionPane.NO_OPTION) {
          println("========")
          runGame()
        }
        println(wordMeaning(0).trim + " :: " + wordMeaning(1).trim)
        //println(wordMeaning(2).trim)

    }
    println("========")
    while (true) {
      wordMeanings.foreach { elem =>
        val word = elem(0)
        val meaning = elem(1)
        val sentence = elem(2)
        if (learningMode) {
          if (showMeaningAndAskWord) {
            JOptionPane.showOptionDialog(null,
              meaning,
              s"meaning $count",
              JOptionPane.YES_NO_OPTION,
              JOptionPane.PLAIN_MESSAGE,
              null,
              options,
              options(0))

          }
        } else {
          val result = JOptionPane.showOptionDialog(null,
                                                    word,
                                                    "",
                                                    JOptionPane.YES_NO_OPTION,
                                                    JOptionPane.PLAIN_MESSAGE,
                                                    null,
                                                    options,
                                                    options(0))
          if (result == JOptionPane.NO_OPTION) {
            println("==============")
            runGame()
          }

        }

        println(s"$meaning: $word")
        if (printSentences) println(s"$sentence")
        count += 1
      }
      println("=========")
    }
    return 0
  }

}

object WordMeaningsRandom {
  def main(args: Array[String]): Unit = {
    val scanner = new Scanner(System.in)
    var exerciseLen = 5
    print(s"exerciseLen: (default: $exerciseLen)")
    val exerciseLenInput = scanner.nextLine()
    if (exerciseLenInput.nonEmpty)
      exerciseLen = exerciseLenInput.trim.toInt

    var learningMode = true
//    print(s"learning mode? (default = $learningMode)")
//    val learningModeInput = scanner.nextLine()
//    if (learningModeInput.nonEmpty)
//      learningMode = true

    var showMeaningAndAskWord = true
    print(s"show meaning and ask word? (default = $showMeaningAndAskWord)")
    val showMeaningAndAskWordInput = scanner.nextLine()
    if (showMeaningAndAskWordInput.nonEmpty)
      showMeaningAndAskWord = true

    val printSentences = false
    //if (scanner.next().toLowerCase() == "y") true else false
    val wordMeaningsRandom = new WordMeaningsRandom(exerciseLen,
                                                    learningMode,
                                                    printSentences,
                                                    showMeaningAndAskWord)
    wordMeaningsRandom.runGame()
  }
}
