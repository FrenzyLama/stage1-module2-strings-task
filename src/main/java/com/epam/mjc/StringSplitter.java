package com.epam.mjc;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class StringSplitter {

    /**
     * Splits given string applying all delimeters to it. Keeps order of result substrings as in source string.
     *
     * @param source source string
     * @param delimiters collection of delimiter strings
     * @return List of substrings
     */
    public List<String> splitByDelimiters(String source, Collection<String> delimiters) {
        // Create delimeters regex
        StringBuilder delimitersForSplit = new StringBuilder("[");
        for (String delimiter : delimiters) {
            delimitersForSplit.append(delimiter);
        }
        delimitersForSplit.append("]");

        // Remove empty elements from list result
        List<String> result = new java.util.ArrayList<>(List.of(source.split(delimitersForSplit.toString())));
        result.removeAll(Arrays.asList("", null));
        return result;
    }
}
