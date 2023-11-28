package games

import javax.swing.{JOptionPane, JScrollPane, JTextArea}

class SentencesStudy {

}

object SentencesStudy {
  def showTextAreaAndGetStrings(): String = {
    val title = "Input"
    val textArea = new JTextArea("")
    textArea.setColumns(30)
    textArea.setRows(10)
    textArea.setLineWrap(true)
    textArea.setWrapStyleWord(true)
    textArea.setSize(textArea.getPreferredSize.width, textArea.getPreferredSize.height)
    val ret = JOptionPane.showConfirmDialog(null, new JScrollPane(textArea), title, JOptionPane.OK_OPTION)
    if (0 == ret) return textArea.getText
    else {
      throw new Exception("Exit")
    }
  }
  def main(args: Array[String]): Unit = {
    val input = showTextAreaAndGetStrings()
    val lines = input.split("\\.")
    lines.foreach {
      line => JOptionPane.showMessageDialog(null, line + ".", "", JOptionPane.PLAIN_MESSAGE)
        JOptionPane.showMessageDialog(null, "Remember the line", "", JOptionPane.PLAIN_MESSAGE)
        println(line)
    }
  }
}
