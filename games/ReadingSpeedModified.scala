package TestCodesScala.games

import TestcodesJava.utils.InputDialogDisplayUtils

import java.awt._
import java.awt.event.{ActionEvent, ActionListener}
import java.io.File
import javax.swing._
import scala.io.Source

object ReadingSpeedModified {
  var prevMillisPerWord = 300
  var previousSentence = "No Previous Sentence"

  def main(args: Array[String]): Unit = {
    showTextArea(prevMillisPerWord)
  }

  def showTextArea(millisPerWord: Int = prevMillisPerWord): Int = {
    var inputWithReadingSpeed =
      InputDialogDisplayUtils.showTextAreaAndGetStringsWithReadingSpeed(
        prevMillisPerWord)

    val inputStr =
      if (inputWithReadingSpeed.getInputStr.toLowerCase == "repeat")
        previousSentence
      else inputWithReadingSpeed.getInputStr

    val millisPerWord = inputWithReadingSpeed.getReadingSpeed

    if (inputStr.trim.nonEmpty) {
      displayTextPane(inputStr, millisPerWord)
    }
    0
  }

  def displayTextPane(inputStr: String,
                      millisPerWord: Int = prevMillisPerWord) = {
    // Frame
    val frame = new JFrame("")
    frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE)
    val contentPane = frame.getContentPane

    // TextPane
    val textPane = new JTextPane()
    val font = new Font(Font.MONOSPACED, Font.PLAIN, 18)
    textPane.setText(inputStr)
    textPane.setFont(font)

    // ScrollPane
    val scrollPane = new JScrollPane(textPane)
    scrollPane.setPreferredSize(new Dimension(850, 500))
    scrollPane.setViewportView(textPane)

    // Panel
    val panel = new JPanel()
    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS))
    panel.setBounds(900, 600, 900, 600)

    // timer
    val numWords = inputStr.split("\\s+").length
    val timer = new Timer(millisPerWord * numWords, new AbstractAction() {
      override def actionPerformed(e: ActionEvent): Unit = {
        frame.dispose()
        showTextArea()
      }
    })

    // Button
    val button = new JButton("Done")
    button.setBounds(900, 1000, 100, 20)
    button.addActionListener(new ActionListener() {
      override def actionPerformed(e: ActionEvent): Unit = {
        timer.stop()
        frame.dispose()
        showTextArea()
      }
    })
    panel.add(scrollPane)
    panel.add(button, BorderLayout.CENTER)

    frame.add(panel)
    frame.setSize(900, 600)
    frame.setVisible(true)
    frame.setLocationRelativeTo(null)

    timer.setRepeats(false)
    previousSentence = inputStr
    prevMillisPerWord = millisPerWord
    timer.start()
  }

  def readFile(fileName: String): Seq[String] = {
    Source.fromFile(new File(fileName)).getLines().toSeq
  }
}
