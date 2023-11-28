package TestcodesJava.utils;

import lombok.Value;

@Value
public class TextWithReadingSpeed {

    private final String inputStr;

    private final int readingSpeed;

    public TextWithReadingSpeed(String inputStr, int readingSpeed) {
        this.inputStr = inputStr;
        this.readingSpeed = readingSpeed;
    }

    public String getInputStr() {
        return inputStr;
    }

    public int getReadingSpeed() {
        return readingSpeed;
    }
}
