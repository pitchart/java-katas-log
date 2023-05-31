package com.kata.elections;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 *
 */
public class ElectionsResults {
    static Map<String, String> displayResults(Map<String, Float> resultsByCandidate, float blankResult, float nullResult, float abstentionResult) {
        Map<String, String> results = new HashMap<>();
        resultsByCandidate.forEach((candidate, result) -> results.put(candidate, String.format(Locale.FRENCH, "%.2f%%", result)));
        results.put("Blank", String.format(Locale.FRENCH, "%.2f%%", blankResult));
        results.put("Null", String.format(Locale.FRENCH, "%.2f%%", nullResult));
        results.put("Abstention", String.format(Locale.FRENCH, "%.2f%%", abstentionResult));
        return results;
    }

    public static Map<String, String> displayResults(ResultsTO resultsTO) {
        return displayResults(resultsTO.getResultsByCandidate(), resultsTO.getBlankResult(), resultsTO.getNullResult(), resultsTO.getAbstentionResult());
    }
}
