package com.kata.elections;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Property of project CUBE. Copyright Decathlon 2023.
 *
 * @author Cube-DC team {@literal <cubegetdreexlille@decathlon.net>}
 */
public class ElectionsWithDistrict implements Elections {
    private List<String> officialCandidates = new ArrayList<>();
    private Map<String, List<String>> electors;
    private Map<String, CandidateVotes> candidateVotesByDistrict;


    public ElectionsWithDistrict(Map<String, List<String>> electors) {
        this.electors = electors;
        candidateVotesByDistrict = electors.keySet().stream().collect(Collectors.toMap(district -> district, district -> new CandidateVotes()));
    }

    @Override
    public void addCandidate(String candidate) {
        officialCandidates.add(candidate);
    }

    @Override
    public void voteFor(String elector, String candidate, String electorDistrict) {
        if (!candidateVotesByDistrict.containsKey(electorDistrict)) {
            return;
        }
        candidateVotesByDistrict.get(electorDistrict).addVote(candidate);
    }

    @Override
    public Map<String, String> results() {
        Map<String, String> results = new HashMap<>();

        int nbVotes = candidateVotesByDistrict.values().stream().mapToInt(CandidateVotes::getNbVotes).sum();
        int nbValidVotes = candidateVotesByDistrict.values().stream().mapToInt(candidateVotes -> candidateVotes.getNbValidVotes(officialCandidates)).sum();
        int blankVotes = candidateVotesByDistrict.values().stream().mapToInt(CandidateVotes::getNbBlankVotes).sum();
        int nullVotes = nbVotes - nbValidVotes - blankVotes;

        Map<String, Integer> officialCandidatesResult = new HashMap<>();
        candidateVotesByDistrict.values()
                .stream()
                .map(candidateVotes -> candidateVotes.getWinner(officialCandidates))
                .forEach(winner-> officialCandidatesResult.put(winner, officialCandidatesResult.getOrDefault(winner, 0) + 1));


        for (int i = 0; i < officialCandidates.size(); i++) {
            Float ratioCandidate = ((float) officialCandidatesResult.getOrDefault(officialCandidates.get(i),0)) / officialCandidates.size() * 100;
            results.put(officialCandidates.get(i), String.format(Locale.FRENCH, "%.2f%%", ratioCandidate));
        }

        float blankResult = ((float) blankVotes * 100) / nbVotes;

        results.put("Blank", String.format(Locale.FRENCH, "%.2f%%", blankResult));

        float nullResult = ((float) nullVotes * 100) / nbVotes;
        results.put("Null", String.format(Locale.FRENCH, "%.2f%%", nullResult));

        int nbElectors = electors.values().stream().map(List::size).reduce(0, Integer::sum);
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        float abstentionResult = 100 - ((float) nbVotes * 100 / nbElectors);
        results.put("Abstention", String.format(Locale.FRENCH, "%.2f%%", abstentionResult));

        return results;
    }
}
