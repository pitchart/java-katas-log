package com.kata.elections;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 *
 */
public class ElectionsResults {

    public static Map<String, String> displayResults(ResultsTO resultsTO) {
        Map<String, String> results = new HashMap<>();
        resultsTO.getResultsByCandidate().forEach((candidate, result) -> results.put(candidate, format(result)));
        results.put(VotesType.BLANK.getType(), format(resultsTO.getBlankResult()));
        results.put(VotesType.NULL.getType(), format(resultsTO.getNullResult()));
        results.put(VotesType.ABSTENTION.getType(), format(resultsTO.getAbstentionResult()));
        return results;
    }

    private static String format(float blankResult) {
        return String.format(Locale.FRENCH, "%.2f%%", blankResult);
    }
}
