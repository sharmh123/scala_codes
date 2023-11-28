package TestcodesJava.games;

import TestcodesJava.TextException;
import TestcodesJava.utils.InputDialogDisplayUtils;
import lombok.Builder;
import lombok.NonNull;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static TestcodesJava.core.DNBCore.readFile;

public class SplitSentences {

    private static final int NUM_WORDS_PER_SENTENCE = 15;
    private static final String BLANK_SPACES_REGEX = "\\s+";
    private static final String BASE_DIR = "/Users/himanshusharma/IdeaProjects/resources/DNB/sentences";
    private static final String AREA_OF_FOCUS_FILENAME = BASE_DIR + "/" + "areaOfFocus";
    private static final String START_INDEX = "startIndex";
    private static final String INPUT_FILE_NAME = "inputFileName";
    private static final String END_INDEX = "endIndex";
    private static final Pattern BLANK_SPACES_REGEX_PATTERN = Pattern.compile(BLANK_SPACES_REGEX);
    private static final Pattern FULL_STOP_PATTERN = Pattern.compile("\\. ");
    private static final Pattern QUESTION_MARK_PATTERN = Pattern.compile("\\? ");
    private static final Pattern BLANK_SPACES_PATTERN = Pattern.compile(BLANK_SPACES_REGEX);
    private static final int NUM_REPITITIONS = 3;
    private static final int DEFAULT_START_INDEX = 0;
    private static final int DEFAULT_END_INDEX = 1000;
    private final int startIndex;
    private final int endIndex;
    private final String inputFileName;

    @Builder
    private SplitSentences(final int startIndex, final int endIndex, final @NonNull String inputFileName) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.inputFileName = getFullFilePath(inputFileName);
        validateInput(startIndex, endIndex, inputFileName);
    }

    private String getFullFilePath(final String inputFileName) {
        return String.format("%s/%s", BASE_DIR, inputFileName);
    }

    private void validateInput(final int startIndex, final int endIndex, @NonNull final String inputFileName) {
        checkStartAndEndIndexes(startIndex, endIndex);
    }

    private void checkStartAndEndIndexes(final int startIndex, final int endIndex) {
        if (endIndex < startIndex) {
            throw new IllegalArgumentException(
                    String.format("startIndex: %s cannot be less endIndex: %s", startIndex, endIndex));
        }
    }

    public void runGame() throws TextException {
        final List<String> inputSentences = extractSentencesFromFile(inputFileName);
        final List<String> exerciseSentences = getExerciseSentencesWithinTheInputRange(inputSentences, inputSentences);
        final List<String> splitExerciseSentences = getSplitSentencesBasedOnMaxNumberOfWordsPerSentence(
                exerciseSentences);
        for (int i = 0; i < NUM_REPITITIONS; i++) {
            InputDialogDisplayUtils.displaySplitSentences(splitExerciseSentences);
        }
    }

    private List<String> getSplitSentencesBasedOnMaxNumberOfWordsPerSentence(final List<String> exerciseSentences) {
        final List<String> splitExerciseSentences = new ArrayList<>();
        for (final String sentence : exerciseSentences) {
            if (sentenceHasMoreThanMaxAllowableNumberOfWords(sentence)) {
                splitExerciseSentences.addAll(getSplitsOfASentenceLargerThanThreshold(sentence));
            } else {
                splitExerciseSentences.add(sentence);
            }
        }
        return splitExerciseSentences;
    }

    private List<String> getSplitsOfASentenceLargerThanThreshold(final String largeSentence) {
        final List<String> splitPartsOfASingleSentence = new ArrayList();
        final List<String> wordsInTheSentence = getWordsInTheSentence(largeSentence);
        StringBuffer buffer = new StringBuffer();
        int bufferCount = 0;
        for (final String word : wordsInTheSentence) {
            buffer.append(" ").append(word);
            if (NUM_WORDS_PER_SENTENCE - 1 == bufferCount) {
                splitPartsOfASingleSentence.add(buffer.toString());
                buffer = new StringBuffer();
            }
            bufferCount = (bufferCount + 1) % NUM_WORDS_PER_SENTENCE;
        }
        if (0 < bufferCount) {
            splitPartsOfASingleSentence.add(buffer.toString());
        }
        return splitPartsOfASingleSentence;
    }

    private List<String> getWordsInTheSentence(final String largeSentence) {
        return Arrays.asList(BLANK_SPACES_REGEX_PATTERN.split(largeSentence));
    }

    private static boolean sentenceHasMoreThanMaxAllowableNumberOfWords(final String sentence) {
        return NUM_WORDS_PER_SENTENCE <= BLANK_SPACES_PATTERN.split(sentence).length;
    }

    private List<String> getExerciseSentencesWithinTheInputRange(final List<String> inputText,
            final List<String> allSentences) {
        return allSentences.subList(
                startIndex, Math.min(endIndex, inputText.size()));
    }

    private static List<String> extractSentencesFromFile(final String inputFile) {
        final List<String> lines = readFile(inputFile);
        return lines.stream()
                .flatMap(x -> Arrays.stream(FULL_STOP_PATTERN.split(x)))
                .flatMap(x -> Arrays.stream(QUESTION_MARK_PATTERN.split(x)))
                .collect(Collectors.toList());
    }

    public static int getAppropriateIndexValue(final IndexType indexType, final String value) {
        if (IndexType.START.equals(indexType)) {
            return getIndexValue(value, DEFAULT_START_INDEX);
        }

        return getIndexValue(value, DEFAULT_END_INDEX);
    }

    private static int getIndexValue(final String value, final int defaultStartIndex) {
        if (StringUtils.isBlank(value)) {
            return defaultStartIndex;
        } else {
            return Integer.parseInt(value);
        }
    }

    public static void main(final String[] args) throws IOException, NumberFormatException, TextException {
        final Map<String, String> inputMap = InputDialogDisplayUtils.showSimpleDialog(AREA_OF_FOCUS_FILENAME);
        final SplitSentences game = SplitSentences.builder()
                .inputFileName(inputMap.get(INPUT_FILE_NAME))
                .startIndex(getAppropriateIndexValue(IndexType.START, inputMap.get(START_INDEX)))
                .endIndex(getAppropriateIndexValue(IndexType.END, inputMap.get(END_INDEX)))
                .build();
        game.runGame();
    }
}
