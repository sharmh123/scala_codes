package TestcodesJava.utils;

import TestcodesJava.TextException;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;

public class InputDialogDisplayUtils {

    private static final String INPUT_FILE_NAME = "inputFileName";
    private static final String START_INDEX = "startIndex";
    private static final String END_INDEX = "endIndex";

    public static Map<String, String> showSimpleDialog(String areaOfFocusFileName) throws IOException {
        JPanel jPanel = new JPanel(new GridLayout(4, 10));
        BufferedReader bufferedReader = new BufferedReader(new FileReader(areaOfFocusFileName));
        Map<String, String> map = new HashMap<String, String>();
        try {
            String line = "";
            while (null != (line = bufferedReader.readLine())) {
                String[] lineArr = line.split("=");
                map.put(lineArr[0].trim(), lineArr[1].trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bufferedReader.close();
        }
        JTextField fileName = new JTextField(map.get(InputDialogDisplayUtils.INPUT_FILE_NAME));
        JTextField startIndex = new JTextField(map.get(InputDialogDisplayUtils.START_INDEX));
        JTextField endIndex = new JTextField(map.get(InputDialogDisplayUtils.END_INDEX));

        JLabel fileNameLabel = new JLabel("File Name: ");
        JLabel startIndexLabel = new JLabel("Start Index: ");
        JLabel endIndexLabel = new JLabel("End Index: ");

        jPanel.add(fileNameLabel);
        jPanel.add(fileName);
        jPanel.add(startIndexLabel);
        jPanel.add(startIndex);
        jPanel.add(endIndexLabel);
        jPanel.add(endIndex);

        int result = JOptionPane.showConfirmDialog(null, jPanel, "DNBStudy ",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        Map<String, String> inputMap = new LinkedHashMap<String, String>();
        if (JOptionPane.OK_OPTION == result) {
            inputMap.put(InputDialogDisplayUtils.INPUT_FILE_NAME, fileName.getText());
            inputMap.put(InputDialogDisplayUtils.START_INDEX, startIndex.getText());
            inputMap.put(InputDialogDisplayUtils.END_INDEX, endIndex.getText());
            InputDialogDisplayUtils.serializeThePreferences(inputMap, areaOfFocusFileName);

            return inputMap;

        } else {
            System.exit(0);
        }

        return null;
    }


    public static void serializeThePreferences(Map<String, String> inputMap, String cfgFileName)
            throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(cfgFileName)));
        for (Map.Entry<String, String> entry : inputMap.entrySet()) {
            bw.write(entry.getKey() + " = " + entry.getValue() + "\r\n");
        }
        bw.flush();
        bw.close();
    }

    public static void displaySplitSentences(List<String> splitExerciseSentences) throws TextException {
        Object[] options = {"Next", "Previous", "Restart"};
        int count = 0;
        int totalCount = splitExerciseSentences.size();
        int listIndex = 0;
        while ((totalCount - 1) >= listIndex) {
            String title = String.format("Sentence: %d. total: %d", count, totalCount);
            String sentence = splitExerciseSentences.get(listIndex);
            JLabel sentenceLabel = new JLabel(sentence);
            sentenceLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            int result = JOptionPane.showOptionDialog(null, sentenceLabel, title,
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            if (JOptionPane.NO_OPTION == result) {
                if (0 < listIndex) {
                    listIndex--;
                    count--;
                    System.out.println(sentence);
                }
            } else if (JOptionPane.YES_OPTION == result) {
                listIndex++;
                count++;
                System.out.println(sentence);
            } else {
                throw new TextException();
            }
        }
    }

    public static void displaySplitSentencesTimed(List<String> splitExerciseSentences, int delayInMillisPerWord) {
        int count = 0;
        int totalCount = splitExerciseSentences.size();
        int listIndex = 0;
        JDialog dialog = new JDialog();
        dialog.setModal(true);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int x = (screenSize.width) / 3;
        int y = (screenSize.height) / 2;
        dialog.setLocation(x, y);

        while ((totalCount - 1) >= listIndex) {
            String title = String.format("Sentence: %d. total: %d", count, totalCount);
            String sentence = splitExerciseSentences.get(listIndex);
            JOptionPane optionPane = new JOptionPane(sentence, -1,
                    JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);

            dialog.setTitle(title);
            //dialog.setIconImage(null);
            dialog.setResizable(false);
            dialog.setContentPane(optionPane);

            dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            dialog.pack();
            int numWords = sentence.split("\\s+").length;

            Timer timer = new Timer(delayInMillisPerWord * numWords, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose();
                }
            });
            timer.setRepeats(false);
            timer.start();
            dialog.setVisible(true);
            listIndex++;
        }
    }

    public static String showTextAreaAndGetStrings() {
        String title = "Input";
        JTextArea textArea = new JTextArea("");
        textArea.setColumns(30);
        textArea.setRows(10);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setSize(textArea.getPreferredSize().width, textArea.getPreferredSize().height);
        int ret = JOptionPane
                .showConfirmDialog(null, new JScrollPane(textArea), title, JOptionPane.OK_OPTION);
        if (0 == ret) {
            return textArea.getText();
        } else {
            System.exit(0);
        }

        return null;
    }

    public static String showTextAreaAndGetStringsWithRepeat() {
        String title = "Input";
        JTextArea textArea = new JTextArea("");
        textArea.setColumns(50);
        textArea.setRows(20);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setSize(textArea.getPreferredSize().width, textArea.getPreferredSize().height);

        // TextField
        JTextField readingSpeedTextField = new JTextField("");

        // Panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBounds(900, 600, 900, 600);

        panel.add(new JScrollPane(textArea));
        panel.add(readingSpeedTextField);

        Object[] options = new Object[]{"OK", "Repeat", "Cancel"};
        int ret = JOptionPane.showOptionDialog(null, new JScrollPane(textArea), title,
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        switch (ret) {
            case 0:
                return textArea.getText();
            case 1:
                return "repeat";
            default:
                System.exit(0);
        }

        return null;
    }

    public static TextWithReadingSpeed showTextAreaAndGetStringsWithReadingSpeed(int defaultSpeed) {
        String title = "Input";
        JTextArea textArea = new JTextArea("");
        textArea.setColumns(50);
        textArea.setRows(20);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setSize(textArea.getPreferredSize().width, textArea.getPreferredSize().height);

        // TextField
        JTextField readingSpeedTextField = new JTextField(String.valueOf(defaultSpeed));

        // Panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBounds(900, 600, 900, 600);

        panel.add(new JScrollPane(textArea));
        panel.add(readingSpeedTextField);

        Object[] options = new Object[]{"OK", "Repeat", "Cancel"};
        int ret = JOptionPane.showOptionDialog(null, panel, title,
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        switch (ret) {
            case 0:
                return new TextWithReadingSpeed(textArea.getText(), Integer.parseInt(readingSpeedTextField.getText()));
            case 1:
                return new TextWithReadingSpeed("repeat", Integer.parseInt(readingSpeedTextField.getText()));
            default:
                System.exit(0);
        }

        return null;
    }
}