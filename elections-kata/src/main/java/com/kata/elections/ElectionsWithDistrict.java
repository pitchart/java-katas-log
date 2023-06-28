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
    private List<String> candidates = new ArrayList<>();
    private List<String> officialCandidates = new ArrayList<>();
    private Map<String, ArrayList<Integer>> votesWithDistricts;
    private Map<String, List<String>> electors;
    private Map<String, CandidateVotes> candidateVotesByDistrict;


    public ElectionsWithDistrict(Map<String, List<String>> electors) {
        this.electors = electors;
        votesWithDistricts = electors.keySet().stream().collect(Collectors.toMap(district -> district, district -> new ArrayList<>()));
        candidateVotesByDistrict = electors.keySet().stream().collect(Collectors.toMap(district -> district, district -> new CandidateVotes()));
    }

    @Override
    public void addCandidate(String candidate) {
        officialCandidates.add(candidate);
        candidates.add(candidate);
        votesWithDistricts.forEach((k, v) -> v.add(0));
    }

    @Override
    public void voteFor(String elector, String candidate, String electorDistrict) {
        if (!candidateVotesByDistrict.containsKey(electorDistrict)) {
            return;
        }
        if (!candidates.contains(candidate)) {
            candidates.add(candidate);
            votesWithDistricts.forEach((district, votes) -> {
                votes.add(0);
            });
        }
        int index = candidates.indexOf(candidate);
        ArrayList<Integer> districtVotes = votesWithDistricts.get(electorDistrict);
        districtVotes.set(index, districtVotes.get(index) + 1);
        candidateVotesByDistrict.get(electorDistrict).addVote(candidate);
    }

    @Override
    public Map<String, String> results() {
        Map<String, String> results = new HashMap<>();
        int nbVotes = 0;
        int nullVotes = 0;
        int blankVotes = 0;
        int nbValidVotes = 0;

        for (Map.Entry<String, ArrayList<Integer>> entry : votesWithDistricts.entrySet()) {
            ArrayList<Integer> districtVotes = entry.getValue();
            nbVotes += districtVotes.stream().reduce(0, Integer::sum);
        }

        for (int i = 0; i < officialCandidates.size(); i++) {
            int index = candidates.indexOf(officialCandidates.get(i));
            for (Map.Entry<String, ArrayList<Integer>> entry : votesWithDistricts.entrySet()) {
                ArrayList<Integer> districtVotes = entry.getValue();
                nbValidVotes += districtVotes.get(index);
            }
        }

        Map<String, Integer> officialCandidatesResult = new HashMap<>();
        for (int i = 0; i < officialCandidates.size(); i++) {
            officialCandidatesResult.put(candidates.get(i), 0);
        }
        for (Map.Entry<String, ArrayList<Integer>> entry : votesWithDistricts.entrySet()) {
            ArrayList<Float> districtResult = new ArrayList<>();
            ArrayList<Integer> districtVotes = entry.getValue();
            for (int i = 0; i < districtVotes.size(); i++) {
                float candidateResult = 0;
                if (nbValidVotes != 0)
                    candidateResult = ((float) districtVotes.get(i) * 100) / nbValidVotes;
                String candidate = candidates.get(i);
                if (officialCandidates.contains(candidate)) {
                    districtResult.add(candidateResult);
                } else {
                    if (candidates.get(i).isEmpty()) {
                        blankVotes += districtVotes.get(i);
                    } else {
                        nullVotes += districtVotes.get(i);
                    }
                }
            }
            int districtWinnerIndex = 0;
            for (int i = 1; i < districtResult.size(); i++) {
                if (districtResult.get(districtWinnerIndex) < districtResult.get(i))
                    districtWinnerIndex = i;
            }
            officialCandidatesResult.put(candidates.get(districtWinnerIndex), officialCandidatesResult.get(candidates.get(districtWinnerIndex)) + 1);
        }
        for (int i = 0; i < officialCandidatesResult.size(); i++) {
            Float ratioCandidate = ((float) officialCandidatesResult.get(candidates.get(i))) / officialCandidatesResult.size() * 100;
            results.put(candidates.get(i), String.format(Locale.FRENCH, "%.2f%%", ratioCandidate));
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
