package TestcodesJava.games;

import TestcodesJava.TextException;
import TestcodesJava.utils.InputDialogDisplayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import lombok.Builder;

public class SplitSentencesStudy {

    private static final int NUM_WORDS_PER_SENTENCE = 15;
    private static final String BLANK_SPACES_REGEX = "\\s+";
    private static final Pattern BLANK_SPACES_REGEX_PATTERN = Pattern.compile(SplitSentencesStudy.BLANK_SPACES_REGEX);
    private static final Pattern FULL_STOP_PATTERN = Pattern.compile("\\. ");
    private static final Pattern QUESTION_MARK_PATTERN = Pattern.compile("\\? ");
    private static final Pattern BLANK_SPACES_PATTERN = Pattern.compile(SplitSentencesStudy.BLANK_SPACES_REGEX);
    private static final int NUM_REPITITIONS = 10;
    private static final String NEW_LINE_PATTERN = "\n";
    private final String inputStr;

    @Builder
    private SplitSentencesStudy(String inputStr) {
        this.inputStr = inputStr;
    }

    private static boolean sentenceHasMoreThanMaxAllowableNumberOfWords(String sentence) {
        return SplitSentencesStudy.NUM_WORDS_PER_SENTENCE <= SplitSentencesStudy.BLANK_SPACES_PATTERN
                .split(sentence).length;
    }

    private static List<String> extractSentencesFromText(String inputString) {
        List<String> lines = Arrays.asList(inputString.split(SplitSentencesStudy.NEW_LINE_PATTERN));
        return lines.stream()
                .flatMap(x -> Arrays.stream(SplitSentencesStudy.FULL_STOP_PATTERN.split(x)))
                .flatMap(x -> Arrays.stream(SplitSentencesStudy.QUESTION_MARK_PATTERN.split(x)))
                .filter(x -> !x.trim().isEmpty())
                .collect(Collectors.toList());
    }

    public static void execute() {
        String input = InputDialogDisplayUtils.showTextAreaAndGetStrings();
        SplitSentencesStudy game = SplitSentencesStudy.builder()
                .inputStr(input)
                .build();
        try {
            game.runGame();
        } catch (TextException e) {
            SplitSentencesStudy.execute();
        }
    }

    public static void main(String[] args) throws NumberFormatException {
        SplitSentencesStudy.execute();
    }

    public void runGame() throws TextException {
        List<String> inputSentences = SplitSentencesStudy.extractSentencesFromText(inputStr);
        List<String> splitExerciseSentences = getSplitSentencesBasedOnMaxNumberOfWordsPerSentence(
                inputSentences);
        for (int i = 0; i < SplitSentencesStudy.NUM_REPITITIONS; i++) {
            InputDialogDisplayUtils.displaySplitSentences(splitExerciseSentences);
        }
    }

    private List<String> getSplitSentencesBasedOnMaxNumberOfWordsPerSentence(List<String> exerciseSentences) {
        List<String> splitExerciseSentences = new ArrayList<>();
        for (String sentence : exerciseSentences) {
            if (SplitSentencesStudy.sentenceHasMoreThanMaxAllowableNumberOfWords(sentence)) {
                splitExerciseSentences.addAll(getSplitsOfASentenceLargerThanThreshold(sentence));
            } else {
                splitExerciseSentences.add(sentence);
            }
        }
        return splitExerciseSentences;
    }

    private List<String> getSplitsOfASentenceLargerThanThreshold(String largeSentence) {
        List<String> splitPartsOfASingleSentence = new ArrayList<>();
        List<String> wordsInTheSentence = getWordsInTheSentence(largeSentence);
        StringBuffer buffer = new StringBuffer();
        int bufferCount = 0;
        for (String word : wordsInTheSentence) {
            buffer.append(" ").append(word);
            if (SplitSentencesStudy.NUM_WORDS_PER_SENTENCE - 1 == bufferCount) {
                splitPartsOfASingleSentence.add(buffer.toString());
                buffer = new StringBuffer();
            }
            bufferCount = (bufferCount + 1) % SplitSentencesStudy.NUM_WORDS_PER_SENTENCE;
        }
        if (0 < bufferCount) {
            splitPartsOfASingleSentence.add(buffer.toString());
        }
        return splitPartsOfASingleSentence;
    }

    private List<String> getWordsInTheSentence(String largeSentence) {
        return Arrays.asList(SplitSentencesStudy.BLANK_SPACES_REGEX_PATTERN.split(largeSentence));
    }
}
