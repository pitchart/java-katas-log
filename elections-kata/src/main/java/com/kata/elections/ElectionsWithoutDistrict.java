package com.kata.elections;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class ElectionsWithoutDistrict implements Elections {
    List<String> candidates = new ArrayList<>();
    List<String> officialCandidates = new ArrayList<>();
    ArrayList<Integer> votes = new ArrayList<>();
    private final Map<String, List<String>> electors;

    private Map<String, Integer> votesByCandidate = new HashMap<>();


    public ElectionsWithoutDistrict(Map<String, List<String>> electors) {
        this.electors = electors;
    }

    @Override
    public void addCandidate(String candidate) {
        officialCandidates.add(candidate);
        candidates.add(candidate);
        votes.add(0);
        votesByCandidate.put(candidate, 0);
    }


    @Override
    public void voteFor(String elector, String candidate, String electorDistrict) {
        if (candidates.contains(candidate)) {
            int index = candidates.indexOf(candidate);
            votes.set(index, votes.get(index) + 1);
        } else {
            // ouch
            candidates.add(candidate);
            // primitive code smell
            votes.add(1);
        }
        if (votesByCandidate.containsKey(candidate)) {
            votesByCandidate.put(candidate, votesByCandidate.get(candidate) + 1);
        } else {
            votesByCandidate.put(candidate, 1);
        }
    }

    @Override
    public Map<String, String> results() {
        Map<String, String> results = new HashMap<>();
        // compute votes
        int nbVotes = votesByCandidate.values().stream().reduce(0, Integer::sum);

        int nbValidVotes = votesByCandidate.entrySet().stream()
                .filter(e -> officialCandidates.contains(e.getKey()))
                .map(Map.Entry::getValue)
                .reduce(0, Integer::sum);
        int blankVotes = votesByCandidate.getOrDefault("", 0);
        int nullVotes = nbVotes - nbValidVotes - blankVotes;
        int nbElectors = electors.values().stream().map(List::size).reduce(0, Integer::sum);

        Map<String, Integer> scoresByCandidate = votesByCandidate.entrySet().stream()
                .filter(e -> officialCandidates.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        // Compute percent
        Map<String, Float> resultsByCandidate = scoresByCandidate.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> (float) e.getValue() * 100 /nbValidVotes));
        float blankResult = ((float) blankVotes * 100) / nbVotes;
        float nullResult = ((float) nullVotes * 100) / nbVotes;
        float abstentionResult = 100 - ((float) nbVotes * 100 / nbElectors);

        // Display results
        resultsByCandidate.forEach((candidate,result) -> results.put(candidate, String.format(Locale.FRENCH, "%.2f%%", result)));
        results.put("Blank", String.format(Locale.FRENCH, "%.2f%%", blankResult));
        results.put("Null", String.format(Locale.FRENCH, "%.2f%%", nullResult));
        results.put("Abstention", String.format(Locale.FRENCH, "%.2f%%", abstentionResult));

        return results;
    }

}
