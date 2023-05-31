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
        ResultTO resultTO = new ResultTO();
        resultTO.nbVotes = votesByCandidate.values().stream().reduce(0, Integer::sum);

        resultTO.nbValidVotes = votesByCandidate.entrySet().stream()
                .filter(e1 -> officialCandidates.contains(e1.getKey()))
                .map(Map.Entry::getValue)
                .reduce(0, Integer::sum);
        resultTO.nbBlankVotes = votesByCandidate.getOrDefault("", 0);

        resultTO.nullVotes = resultTO.nbVotes - votesByCandidate.entrySet().stream()
                .filter(e11 -> officialCandidates.contains(e11.getKey()))
                .map(Map.Entry::getValue)
                .reduce(0, Integer::sum) - votesByCandidate.getOrDefault("", 0);

        resultTO.nbElectors = electors.values().stream().map(List::size).reduce(0, Integer::sum);

        resultTO.scoresByCandidate = votesByCandidate.entrySet().stream()
                .filter(e21 -> officialCandidates.contains(e21.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        // Compute percent
        Map<String, Float> resultsByCandidate = votesByCandidate.entrySet().stream()
                .filter(e2 -> officialCandidates.contains(e2.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> (float) e.getValue() * 100 / votesByCandidate.entrySet().stream()
                        .filter(e1 -> officialCandidates.contains(e1.getKey()))
                        .map(Map.Entry::getValue)
                        .reduce(0, Integer::sum)));
        float blankResult = ((float) (int) votesByCandidate.getOrDefault("", 0) * 100) / resultTO.nbVotes;
        float nullResult = ((float) (resultTO.nbVotes - votesByCandidate.entrySet().stream()
                        .filter(e11 -> officialCandidates.contains(e11.getKey()))
                        .map(Map.Entry::getValue)
                        .reduce(0, Integer::sum) - votesByCandidate.getOrDefault("", 0)) * 100) / resultTO.nbVotes;
        float abstentionResult = 100 - ((float) resultTO.nbVotes * 100 / electors.values().stream().map(List::size).reduce(0, Integer::sum));

        return ElectionsResults.displayResults(resultsByCandidate, blankResult, nullResult, abstentionResult);
    }

}
