package com.kata.elections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ElectionsWithoutDistrict implements Elections {
    List<String> officialCandidates = new ArrayList<>();
    private final Map<String, List<String>> electors;

    private Map<String, Integer> votesByCandidate = new HashMap<>();


    public ElectionsWithoutDistrict(Map<String, List<String>> electors) {
        this.electors = electors;
    }

    @Override
    public void addCandidate(String candidate) {
        officialCandidates.add(candidate);
        votesByCandidate.put(candidate, 0);
    }


    @Override
    public void voteFor(String elector, String candidate, String electorDistrict) {
        if (votesByCandidate.containsKey(candidate)) {
            votesByCandidate.put(candidate, votesByCandidate.get(candidate) + 1);
        } else {
            votesByCandidate.put(candidate, 1);
        }
    }

    @Override
    public Map<String, String> results() {
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

        return ElectionsResults.displayResults(resultsByCandidate, blankResult, nullResult, abstentionResult);
    }

}
