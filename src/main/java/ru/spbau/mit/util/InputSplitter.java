package ru.spbau.mit.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Эдгар on 21.10.2016.
 * Small class for splitting input by specified delimiters
 * with respect to quotes
 */
public class InputSplitter {
    /**
     * Method that splits its' input into array of strings
     * with respect to quotes. Namely, if delimiter is placed
     * inside the quotes, then it is ignored
     * @param rawInput - string to split
     * @param delimiter - char to split by(whitespace, pipe sign, etc)
     * @return array of string which are parts of input string
     */
    public static List<String> splitBy(String rawInput, char delimiter) {
        List<String> result = new ArrayList<>();
        boolean inStrongQuotes = false;
        boolean inWeakQuotes = false;
        int left = 0;
        for (int right = 0; right < rawInput.length(); ++right) {
            char chr = rawInput.charAt(right);
            if (!inStrongQuotes && !inWeakQuotes && chr == delimiter) {
                result.add(rawInput.substring(left, right));
                left = right + 1;
            }
            if (chr == '\'') {
                inStrongQuotes ^= true;
            }
            if (chr == '\"') {
                inWeakQuotes ^= true;
            }
        }
        result.add(rawInput.substring(left));
        result.replaceAll(String::trim);
        return result;
    }
}
