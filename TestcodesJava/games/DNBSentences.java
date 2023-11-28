package TestcodesJava.games;

import TestcodesJava.config.ConfigContainer;
import TestcodesJava.core.DNBCore;

import static TestcodesJava.core.DNBCore.*;

import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;


public class DNBSentences {

    private final String configFileName;
    private final String inputSentenceType = "sentence";
    private final ConfigContainer configContainer;

    public DNBSentences() {
        configFileName = DNBCore.getDNBConfigDir() + "/sentences_config.txt";
        configContainer = new ConfigContainer(configFileName);
        configContainer.setInputSentenceType(inputSentenceType);
    }

    public static void main(String[] args) throws Exception {
        DNBSentences game = new DNBSentences();
        game.runGame();
    }

    public void runGame() throws Exception {
        List<String> allWords = readInputFile(configContainer.getInputFileName());
        Collections.shuffle(allWords);
        List<String> exerciseWords = allWords.subList(0, configContainer.getExerciseLen());
        //DNBCore.startGameExecution(exerciseWords, configContainer);
        int i = 1;
        for (String exerciseWord : exerciseWords) {
            JOptionPane.showMessageDialog(null, exerciseWord, configContainer.getInputSentenceType()
                    + ": " + i, JOptionPane.PLAIN_MESSAGE);
            i++;
            System.out.println(exerciseWord);
        }
        //DNBSentencesCore.displayResults(configContainer);
    }
}


