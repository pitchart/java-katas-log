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
        VoteCountFactory voteCountFactory = new VoteCountFactory();
        VoteCountTo voteCountTo = voteCountFactory.getVoteCountTo(electors, officialCandidates, votesByCandidate);

        // Compute percent
        Map<String, Float> resultsByCandidate = votesByCandidate.entrySet().stream()
                .filter(e2 -> officialCandidates.contains(e2.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> (float) e.getValue() * 100 / votesByCandidate.entrySet().stream()
                        .filter(e1 -> officialCandidates.contains(e1.getKey()))
                        .map(Map.Entry::getValue)
                        .reduce(0, Integer::sum)));
        float blankResult = ((float) (int) votesByCandidate.getOrDefault("", 0) * 100) / voteCountTo.getNbVotes();
        float nullResult = ((float) (voteCountTo.getNbVotes() - votesByCandidate.entrySet().stream()
                        .filter(e11 -> officialCandidates.contains(e11.getKey()))
                        .map(Map.Entry::getValue)
                        .reduce(0, Integer::sum) - votesByCandidate.getOrDefault("", 0)) * 100) / voteCountTo.getNbVotes();
        float abstentionResult = 100 - ((float) voteCountTo.getNbVotes() * 100 / electors.values().stream().map(List::size).reduce(0, Integer::sum));

        return ElectionsResults.displayResults(resultsByCandidate, blankResult, nullResult, abstentionResult);
    }

}
