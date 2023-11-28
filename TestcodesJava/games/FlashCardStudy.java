package TestcodesJava.games;

import TestCodesScala.games.QuestionAndAnswer;
import TestcodesJava.utils.PassageWithQuestionAnswers;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import javax.swing.*;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class FlashCardStudy {

    public static final String DELIMITER = "|";
    public static final String PIPE_SEPARATOR = "\\|";
    public static final int MILLIS_PER_WORD = 400;
    public static final String PASSAGE_SEPARATOR_STRING = "break";

    public static void displayTextPane(PassageWithQuestionAnswers passageWithQuestionAnswers, int millisPerWord) {
        // Frame
        JDialog frame = new JDialog();
        frame.setModal(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // TextPane
        JTextPane textPane = new JTextPane();
        Font font = new Font(Font.MONOSPACED, Font.PLAIN, 18);
        String passage = passageWithQuestionAnswers.getPassage();
        textPane.setText(passage);
        textPane.setFont(font);

        // ScrollPane
        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setPreferredSize(new Dimension(850, 500));
        scrollPane.setViewportView(textPane);

        // Panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBounds(900, 600, 900, 600);

        // timer
        int numWords = passage.split("\\s+").length;

        // Button
        JButton button = new JButton("Done");
        button.setBounds(900, 1000, 100, 20);
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        panel.add(scrollPane);
        panel.add(button, BorderLayout.CENTER);

        frame.add(panel);
        frame.setSize(900, 600);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public static void main(String[] args) throws IOException {
        List<String> options = FlashCardStudy.showSimpleDialog();
        String fileName = options.get(0);
        // Excel sheet visible row indexes start with 1
        int startIndex = Integer.parseInt(options.get(1)) - 1;
        int endIndex = Integer.parseInt(options.get(2)) - 1;

        String sheetName = options.get(3);
        FileInputStream file = new FileInputStream(new File(fileName));

        XSSFWorkbook wb = new XSSFWorkbook(file);
        XSSFSheet sheet = null;
        if (sheetName.equals("default") || sheetName.isEmpty()) {
            sheet = wb.getSheetAt(0);
        } else {
            sheet = wb.getSheet(sheetName);
        }

        List<PassageWithQuestionAnswers> passagesWithQuestionAnswers = FlashCardStudy
                .getPassagesWithQuestionsAndAnswers(sheet, startIndex, endIndex);

        for (PassageWithQuestionAnswers passageWithQnA : passagesWithQuestionAnswers) {
            FlashCardStudy.displayTextPane(passageWithQnA, FlashCardStudy.MILLIS_PER_WORD);
            FlashCardStudy.displayQuestionAndAnswers(passageWithQnA);
        }
    }

    private static void displayQuestionAndAnswers(PassageWithQuestionAnswers passageWithQnA) {
        for (QuestionAndAnswer questionAndAnswer : passageWithQnA.getQuestionAndAnswerList()) {
            JOptionPane.showMessageDialog(null, questionAndAnswer.question(), "", JOptionPane.PLAIN_MESSAGE);
            JOptionPane.showMessageDialog(null, questionAndAnswer.answer(), "", JOptionPane.PLAIN_MESSAGE);
        }
    }

    private static List<String> getLines(XSSFSheet sheet, int startIndex, int endIndex) {
        List<String> lines = new ArrayList();
        for (Row row : sheet) {
            if (row.getRowNum() < startIndex) {
                continue;
            }
            if (row.getRowNum() > endIndex) {
                break;
            }

            String question = row.getCell(0).toString();
            if (question.isEmpty()) {
                continue;
            }
            if (question.equalsIgnoreCase(FlashCardStudy.PASSAGE_SEPARATOR_STRING)) {
                lines.add(question);
                continue;
            }
            String fullLine = question;
            if ((row.getCell(1) != null) && (!row.getCell(1).toString().isEmpty())) {
                fullLine = fullLine + FlashCardStudy.DELIMITER + row.getCell(1);
            }
            lines.add(fullLine);
        }
        return lines;
    }

    private static List<PassageWithQuestionAnswers> getPassagesWithQuestionsAndAnswers(XSSFSheet sheet, int startIndex,
            int endIndex) {
        List<String> lines = FlashCardStudy.getLines(sheet, startIndex, endIndex);

        List<List<String>> passageSections = new ArrayList();

        List<String> passageLines = new ArrayList();
        for (String line : lines) {
            if (line.equalsIgnoreCase(FlashCardStudy.PASSAGE_SEPARATOR_STRING)) {
                passageSections.add(passageLines);
                passageLines = new ArrayList();
                continue;
            }
            passageLines.add(line);
        }
        passageSections.add(passageLines);

        List<PassageWithQuestionAnswers> passageWithQuestionAnswersList = FlashCardStudy
                .getPassagesSections(passageSections);

        return passageWithQuestionAnswersList;
    }

    private static List<PassageWithQuestionAnswers> getPassagesSections(List<List<String>> passageSections) {
        List<PassageWithQuestionAnswers> passagesWithQuestionAnswers = new ArrayList();
        for (List<String> passageSection : passageSections) {
            List<QuestionAndAnswer> questionAndAnswerList = new ArrayList();
            for (int i = 1; i < passageSection.size(); i++) {
                String[] splits = passageSection.get(i).split(FlashCardStudy.PIPE_SEPARATOR);
                questionAndAnswerList.add(new QuestionAndAnswer(splits[0], splits[1]));
            }
            PassageWithQuestionAnswers passageWithQuestionAnswers = new PassageWithQuestionAnswers(
                    passageSection.get(0), questionAndAnswerList);

            passagesWithQuestionAnswers.add(passageWithQuestionAnswers);
        }
        return passagesWithQuestionAnswers;
    }

    public static List<String> showSimpleDialog() throws IOException {
        JPanel jPanel = new JPanel(new GridLayout(4, 3));
        String cfgFileName = "/Users/himash/testcodes/src/resources/DNB/flashCards/FlashCardsCfg.txt";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(cfgFileName));
        Map<String, String> map = new HashMap<String, String>();
        try {
            String line = "";
            while (null != (line = bufferedReader.readLine())) {
                if (!line.isEmpty()) {
                    String[] lineArr = line.split("=");
                    map.put(lineArr[0].trim(), lineArr[1].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bufferedReader.close();
        }
        JTextField fileName = new JTextField(map.get("fileName"));
        JTextField startIndex = new JTextField(map.get("startIndex"));
        JTextField endIndex = new JTextField(map.get("endIndex"));
        JTextField sheetName = new JTextField(map.get("sheetName"));

        JLabel fileNameLabel = new JLabel("File Name: ");
        JLabel startIndexLabel = new JLabel("Start Index: ");
        JLabel endIndexLabel = new JLabel("End Index: ");
        JLabel sheetNameLabel = new JLabel("Sheet Name");

        jPanel.add(fileNameLabel);
        jPanel.add(fileName);
        jPanel.add(startIndexLabel);
        jPanel.add(startIndex);
        jPanel.add(endIndexLabel);
        jPanel.add(endIndex);
        jPanel.add(sheetNameLabel);
        jPanel.add(sheetName);

        int result = JOptionPane.showConfirmDialog(null, jPanel, "FlashCards",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        Map<String, String> inputMap = new LinkedHashMap<String, String>();
        if (result == JOptionPane.OK_OPTION) {
            List<String> stringList = new ArrayList<String>();
            stringList.add(fileName.getText());
            inputMap.put("fileName", fileName.getText());

            stringList.add(startIndex.getText());
            inputMap.put("startIndex", startIndex.getText());

            stringList.add(endIndex.getText());
            inputMap.put("endIndex", endIndex.getText());

            stringList.add(sheetName.getText());
            inputMap.put("sheetName", sheetName.getText());

            FlashCardStudy.serializeThePreferences(inputMap, cfgFileName);
            return stringList;
        } else {
            System.exit(0);
        }

        return null;
    }

    private static void serializeThePreferences(Map<String, String> inputMap, String cfgFileName) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(cfgFileName)));
        for (Map.Entry<String, String> entry : inputMap.entrySet()) {
            bw.write(entry.getKey() + " = " + entry.getValue() + "\r\n");
        }
        bw.flush();
        bw.close();
    }

}
