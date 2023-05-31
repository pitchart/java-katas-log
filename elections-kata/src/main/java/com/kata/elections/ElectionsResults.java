package com.kata.elections;

import com.sun.jdi.VoidType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

/**
 *
 */
public class ElectionsResults {

    public Map<String, String> displayResults(ResultsTO resultsTO) {
        Map<String, String> results = new HashMap<>();
        resultsTO.getResultsByCandidate().forEach((candidate, result) -> results.put(candidate, format(result)));
        Arrays.stream(VotesType.values()).forEach(votesType -> results.put(votesType.getType(),format(votesType.getFunction().apply(resultsTO))));
        return results;
    }

    private String format(float blankResult) {
        return String.format(Locale.FRENCH, "%.2f%%", blankResult);
    }
}
