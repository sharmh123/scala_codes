package TestcodesJava.games;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class Reader {

    JFrame guiFrame;    //initiate main window frame
    JPanel optPanel;    //initiate option panel
    JFileChooser fileDialog;    //initiate fileDialog
    JTextPane textPane;     //initiate textPane

    int pause = 265;    //set initial reading speed to 200wpm
    int white = 1;      //tracks the colour scheme
    String filePath;    //holds location of the selected file


    public Reader() //Create JFrame and all components
    {
        guiFrame = new JFrame();    //create new instance of the main window
        guiFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);   //program will exit if window closed
        guiFrame.setTitle("Reader Window");     //title of main window
        guiFrame.setSize(500, 200);      //set the size of the main window
        guiFrame.setLocationRelativeTo(null);   //put the window in the middle of the screen
        guiFrame.setLayout(new BorderLayout()); //initiate layout of guiframe

        fileDialog = new JFileChooser();//"C:\\Documents and Settings\\Owner\\My Documents" used to select file to read

        textPane = new JTextPane();     //create new instance of the textpane
        textPane.setEditable(false);    //user cannot edit text in textpane
        Font font = new Font("Serif", Font.BOLD, 22);   //set font and size
        textPane.setFont(font); //set font of textpane
        StyledDocument doc = textPane.getStyledDocument();  //styled document allows allignment options
        SimpleAttributeSet center = new SimpleAttributeSet();       //attribute set to control center alignment
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);   //Align text to the center
        doc.setParagraphAttributes(0, doc.getLength(), center, false);  //set formatting options
        textPane.setText("Choose a speed, open a file and start reading");  //initial contents of textpane
        textPane.setVisible(true);      //set textpane to visible
        guiFrame.add(textPane, BorderLayout.CENTER);    //add textpane with word output in center to guiframe

        optPanel = new JPanel();        //add an instance of optPanel
        optPanel.setLayout(new GridLayout(1, 3));   //set layout of buttons 1 high by 3 wide
        guiFrame.add(optPanel, BorderLayout.NORTH);  //Put the buttons at the bottom of the window

        JButton openButton = new JButton("Open File");  //Create new button
        openButton.setActionCommand("Open File");
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {   //event happens if file button pressed
                try {
                    openFile();     //call open file method to display file selection window
                } catch (Exception e) {     //catch exception
                    e.printStackTrace();    //used to identify problems
                }
            }
        });
        optPanel.add(openButton);   //add open file button to option panel

        JButton speedButton = new JButton("Select WPM");  //Create new button
        speedButton.setActionCommand("Select WPM");
        speedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {   //event happens if user clicks button
                try {
                    setSpeed();     //call set speed method to display speed window
                } catch (Exception e) { //catch exception
                    e.printStackTrace();    //used to identify problems
                }
            }
        });
        optPanel.add(speedButton);  //Add speed selection button to option panel

        JButton invertColours = new JButton("Invert Colours");  //Add button to invert colour scheme
        invertColours.setActionCommand("Invert Colours");
        invertColours.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {    //event happens when user clicks button
                try {
                    if (white == 1) {       //check the current color settings. Currently black on white
                        textPane.setBackground(Color.black);    //Set background colour of textbox to black
                        textPane.setForeground(Color.white);    //set text colour to white
                        white = 0;          //tracks colour settings
                    } else {                //check the current color settings. currently white on black
                        textPane.setBackground(Color.white);
                        textPane.setForeground(Color.black);
                        white = 1;          //tracks colour settings
                    }
                } catch (Exception e) {   //catch exception
                    e.printStackTrace();    //used to identify problems
                }
            }
        });
        optPanel.add(invertColours);    //add the colour button to option panel

        guiFrame.setVisible(true);
    }

    public static void main(String[] args) {
        //Use the event dispatch thread for Swing components
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Reader();
            }
        });
    }

    private static void sleep(int i) {     //Adds a pause between words. i changes length of pause
        try {
            Thread.sleep(i);        //sleep thread for pause+n milliseconds
        } catch (InterruptedException e) {   //exception needed for sleep
            //do nothing
        }
    }

    //Show a open file dialog box
    private void openFile() throws Exception {  //exception needed to handle files
        int openChoice = fileDialog.showOpenDialog(guiFrame);   //display file chooser window
        if (openChoice == JFileChooser.APPROVE_OPTION) {     //user selects a file from file chooser window
            filePath = fileDialog.getSelectedFile()
                    .getPath();  //set filepath as the path of the file selected in the filedialog window
            (new Thread(new displayText()))
                    .start();    //Start displayText method in separate thread to avoid gui freeze
        }
    }

    private void setSpeed() {        //Method called if speed button pressed
        String[] speeds = {"200", "300", "500", "800"};   //String holds values for drop down box
        JFrame frame = new JFrame("Input required speed in words per minute");  //new frame to select speed
        String speedChoice = (String) JOptionPane.showInputDialog(frame,    //option pane used for easy speed selection
                "What speed would you like?",   //display question to user
                "WPM",          //window title
                JOptionPane.QUESTION_MESSAGE,   //standard for option pane
                null,
                speeds, //add the string of speeds as options
                speeds[0]); //default is 200
        if (speedChoice.equals("200")) {
            pause = 265;                    //187 words in 59.877s that's 187.38414wpm (without natural reading pauses)
        } else if (speedChoice.equals("300")) {
            pause = 160;                    //187 words in 38.522s that's 291.26215wpm (without natural reading pauses)
        } else if (speedChoice.equals("500")) {
            pause = 75;                     //187 words in 22.451s that's 499.75504wpm (without natural reading pauses)
        } else if (speedChoice.equals("800")) {
            pause = 28;                     //187 words in 14.006s that's 801.08527wpm (without natural reading pauses)
        }
    }

    public class displayText implements
            Runnable {  //Run all the display text method within its own thread. Prevents the GUI from becoming unresponsive

        @Override
        public void run() {
            Scanner in = null;     //Scanner to read the file at chosen location
            try {
                in = new Scanner(new FileReader(filePath));
            } catch (FileNotFoundException e) {   //ignore exception
                e.printStackTrace();    //used to identify problems
            }
            int i = 0;       //Number of words in document
            while (in.hasNextLine()) {        //read every line of file
                in.next();      //move to next word
                i++;            //increment word counter
            }
            in.close();     //Close Scanner after words have been counted

            try {
                in = new Scanner(new FileReader(filePath));     //New Scanner to start at top of file
            } catch (FileNotFoundException e) {   //ignore exception
                e.printStackTrace();    //used to identify problems
            }
            String[] word = new String[i];      //Array stores each word

            int n = 0;
            long start = 0, end; //used to calculate words per minute
            float time;     //will hold time taken to finish article
            float wPM;         //will hold number of words per minute

            for (int k = 0; k < i; k++) { //loop cycles through every word in file
                //FROM HERE
                if (k == 0) {       //check if at start of loop
                    start = System.currentTimeMillis();    //Start time used to calculate words per minute
                } else if (k == (i - 1)) {      //check if at end of loop
                    end = System.currentTimeMillis();   //end time used to calculate words per minute
                    time = (end - start);     //time taken = end time - start time
                    wPM = (60 / (time / 1000)) * i;     //words per minute = time in minutes * words read
                    System.out.println(i + " words in " + (time / 1000) + "s" + " that's " + wPM
                            + "wpm");    //print results in system dialog
                }
                //TO HERE is just used to show how many words were read. Used to calculate how much pause to set.

                word[k] = in.next();        //get next word from file
                //Natural reading speeds and pauses attempt (needs research)
                if (word[k].length() >= 5 && word[k].length() <= 8) {
                    n = 100;     //short pause for words with 5-8 characters
                } else if (word[k].length() >= 9 && word[k].length() <= 12) {
                    n = 150;   //medium pause for words with 9-12 characters
                } else if (word[k].length() > 12) {
                    n = 200;       //long pause for words over 12 characters
                }
                for (int j = 0; j < word[k].length(); j++) {      //loop cycles through length of word
                    if (word[k].charAt(word[k].length() - 1) == '.')   //Add pause after a full stop
                    {
                        n += 50;
                    }
                    if (word[k].charAt(word[k].length() - 1) == ',')   //Add slight pause after a comma
                    {
                        n += 30;
                    }
                }
                textPane.setText(word[k]);  //Set the word in the text pane to the word read from file
                Reader.sleep(pause + n);     //call sleep method to add a pause after the word. n adds custom pause
                n = 0;            //n set back to 0 after each word
            }
        }
    }
}
