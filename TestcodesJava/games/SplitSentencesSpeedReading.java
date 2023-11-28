package TestcodesJava.games;

import TestcodesJava.utils.InputDialogDisplayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import lombok.Builder;

public class SplitSentencesSpeedReading {

    public static final int DELAY_IN_MILLIS_PER_WORD = 300;
    private static final int NUM_WORDS_PER_SENTENCE = 15;
    private static final String BLANK_SPACES_REGEX = "\\s+";
    private static final Pattern BLANK_SPACES_REGEX_PATTERN = Pattern.compile(
            SplitSentencesSpeedReading.BLANK_SPACES_REGEX);
    private static final Pattern FULL_STOP_PATTERN = Pattern.compile("\\. ");
    private static final Pattern QUESTION_MARK_PATTERN = Pattern.compile("\\? ");
    private static final Pattern BLANK_SPACES_PATTERN = Pattern.compile(SplitSentencesSpeedReading.BLANK_SPACES_REGEX);
    private static final int NUM_REPITITIONS = 3;
    private static final String NEW_LINE_PATTERN = "\n";
    private final String inputStr;

    @Builder
    private SplitSentencesSpeedReading(String inputStr) {
        this.inputStr = inputStr;
    }

    private static boolean sentenceHasMoreThanMaxAllowableNumberOfWords(String sentence) {
        return SplitSentencesSpeedReading.NUM_WORDS_PER_SENTENCE <= SplitSentencesSpeedReading.BLANK_SPACES_PATTERN
                .split(sentence).length;
    }

    private static List<String> extractSentencesFromText(String inputString) {
        List<String> lines = Arrays.asList(inputString.split(SplitSentencesSpeedReading.NEW_LINE_PATTERN));
        return lines.stream()
                .flatMap(x -> Arrays.stream(SplitSentencesSpeedReading.FULL_STOP_PATTERN.split(x)))
                .flatMap(x -> Arrays.stream(SplitSentencesSpeedReading.QUESTION_MARK_PATTERN.split(x)))
                .filter(x -> x.trim().length() > 0)
                .collect(Collectors.toList());
    }

    public static void execute() throws InterruptedException {
        String input = InputDialogDisplayUtils.showTextAreaAndGetStrings();
        SplitSentencesSpeedReading game = SplitSentencesSpeedReading.builder()
                .inputStr(input)
                .build();
        game.runGame();
    }

    public static void main(String[] args) throws NumberFormatException, InterruptedException {
        SplitSentencesSpeedReading.execute();
    }

    private static List<String> getSplitSentencesBasedOnMaxNumberOfWordsPerSentence(List<String> exerciseSentences) {
        List<String> splitExerciseSentences = new ArrayList<>();
        for (String sentence : exerciseSentences) {
            if (SplitSentencesSpeedReading.sentenceHasMoreThanMaxAllowableNumberOfWords(sentence)) {
                splitExerciseSentences
                        .addAll(SplitSentencesSpeedReading.getSplitsOfASentenceLargerThanThreshold(sentence));
            } else {
                splitExerciseSentences.add(sentence);
            }
        }
        return splitExerciseSentences;
    }

    private static List<String> getSplitsOfASentenceLargerThanThreshold(String largeSentence) {
        List<String> splitPartsOfASingleSentence = new ArrayList<>();
        List<String> wordsInTheSentence = SplitSentencesSpeedReading.getWordsInTheSentence(largeSentence);
        StringBuffer buffer = new StringBuffer();
        int bufferCount = 0;
        for (String word : wordsInTheSentence) {
            buffer.append(" ").append(word);
            if (SplitSentencesSpeedReading.NUM_WORDS_PER_SENTENCE - 1 == bufferCount) {
                splitPartsOfASingleSentence.add(buffer.toString());
                buffer = new StringBuffer();
            }
            bufferCount = (bufferCount + 1) % SplitSentencesSpeedReading.NUM_WORDS_PER_SENTENCE;
        }
        if (0 < bufferCount) {
            splitPartsOfASingleSentence.add(buffer.toString());
        }
        return splitPartsOfASingleSentence;
    }

    private static List<String> getWordsInTheSentence(String largeSentence) {
        return Arrays.asList(SplitSentencesSpeedReading.BLANK_SPACES_REGEX_PATTERN.split(largeSentence));
    }

    public void runGame() throws InterruptedException {
        List<String> inputSentences = SplitSentencesSpeedReading.extractSentencesFromText(inputStr);
        List<String> splitExerciseSentences = SplitSentencesSpeedReading
                .getSplitSentencesBasedOnMaxNumberOfWordsPerSentence(
                        inputSentences);
        for (int i = 0; i < SplitSentencesSpeedReading.NUM_REPITITIONS; i++) {
            InputDialogDisplayUtils.displaySplitSentencesTimed(splitExerciseSentences,
                    SplitSentencesSpeedReading.DELAY_IN_MILLIS_PER_WORD);
        }
    }
}
