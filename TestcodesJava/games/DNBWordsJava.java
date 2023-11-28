package TestcodesJava.games;

import TestcodesJava.config.ConfigContainer;
import TestcodesJava.core.DNBCore;

import static TestcodesJava.core.DNBCore.*;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class DNBWordsJava {

    private final String configFileName;
    private final String inputSentenceType = "word";
    private final ConfigContainer configContainer;

    public DNBWordsJava() {
        String fileName = "words_config.txt";
        configFileName = DNBCore.getDNBConfigDir() + "/" + fileName;
        configContainer = new ConfigContainer(configFileName);
        configContainer.setInputSentenceType(inputSentenceType);
    }

    public static void main(String[] args) throws Exception {
        DNBWordsJava game = new DNBWordsJava();
        game.runGame();
    }

    public static List<String> getSecondListOfWords(int exerciseLen, ConfigContainer configContainer,
            List<String> allWords) {
        Random random = new Random(exerciseLen);
        int randomNumber = random.nextInt(200);
        List<String> secondListOfExerciseWords;
        switch (configContainer.getDualNBackMode()) {
            case "numbers":
                secondListOfExerciseWords = DNBCore.getListOfNumbers(exerciseLen, configContainer);
                break;
            case "colors":
                secondListOfExerciseWords = DNBCore.getListOfColors(exerciseLen, configContainer);
                break;
            default:
                secondListOfExerciseWords = allWords.subList(randomNumber, exerciseLen + randomNumber);
        }

        return secondListOfExerciseWords;
    }

    public List<String> getExerciseWords(int exerciseLen) {
        String inputFileName = configContainer.getInputFileName();
        boolean dualNBack = configContainer.isDualNBack();
        List<String> allWords = readInputFile(inputFileName);

        Collections.shuffle(allWords);
        List<String> exerciseWords = allWords.subList(0, exerciseLen);

        if (dualNBack) {
            List<String> secondListOfExerciseWords = DNBWordsJava
                    .getSecondListOfWords(exerciseLen, configContainer, allWords);
            for (int i = 0; i < secondListOfExerciseWords.size(); i++) {
                String combinedWord = exerciseWords.get(i) + ", " + secondListOfExerciseWords.get(i);
                exerciseWords.set(i, combinedWord);
            }
        }

        return exerciseWords;
    }

    public void runGame() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Exercise Length: ");
        int exerciseLen = scanner.nextInt();
        List<String> exerciseWords = getExerciseWords(exerciseLen);
        DNBCore.startGameExecution(exerciseWords, configContainer);
        DNBCore.displayResults(configContainer);
    }
}


