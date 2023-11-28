package TestcodesJava.games;

import TestcodesJava.config.ConfigContainer;
import TestcodesJava.core.DNBCore;

import java.util.*;
import java.util.stream.Stream;

import static TestcodesJava.core.DNBCore.readInputFile;

public class DNBWords {
    private String configFileName;
    private String inputSentenceType = "word";
    private ConfigContainer configContainer;

    public DNBWords() {
        String baseDir = "/Users/himanshusharma/IdeaProjects/resources/DNB/config";
        String fileName = "words_config.txt";
        this.configFileName = baseDir + "/" + fileName;
        configContainer = new ConfigContainer(configFileName);
        configContainer.setInputSentenceType(inputSentenceType);
    }

    public List<String> getSecondListOfWords(int exerciseLen, ConfigContainer configContainer, List<String> allWords) {
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
                secondListOfExerciseWords = allWords.subList(0 + randomNumber, exerciseLen + randomNumber);
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
            List<String> secondListOfExerciseWords = getSecondListOfWords(exerciseLen, configContainer, allWords);
            for (int i = 0; i < secondListOfExerciseWords.size(); i++) {
                String combinedWord = exerciseWords.get(i) + ", " + secondListOfExerciseWords.get(i);
                exerciseWords.set(i, combinedWord);
            }
        }

        return exerciseWords;
    }

    public int getRandomNumber(int min, int max) {
        return new Random().nextInt(max - min) + min;
    }

    public void runGame(int exerciseLen, int numTerms) throws Exception {
        List<String> exerciseWords = getExerciseWords(exerciseLen);
        if (numTerms >= 2) {
            List<String> integers = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9)
                    .map(Object::toString)
                    .toList();
            exerciseWords = exerciseWords.stream()
                    .map(word -> word + ", " + getRandomNumber(1, 9))
                    .toList();
        }
        DNBCore.startGameExecution(exerciseWords, configContainer);
    }

    public static void main(String[] args) throws Exception {
        DNBWords game = new DNBWords();
        Scanner scanner = new Scanner(System.in);
        int numTerms = 1;
        System.out.print("Number of terms (1 or 2): (default = " +
                numTerms + ")");
        String numTermsInput = scanner.nextLine();
        if (!numTermsInput.trim().equals(""))
            numTerms = Integer.parseInt(numTermsInput);
        int exerciseLen = 200;
        game.runGame(exerciseLen, numTerms);
    }
}


