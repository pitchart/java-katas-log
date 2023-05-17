package com.kata.elections;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ElectionsWithoutDistrict implements ElectionsInterface {
    List<String> candidates = new ArrayList<>();
    List<String> officialCandidates = new ArrayList<>();
    ArrayList<Integer> votesWithoutDistricts = new ArrayList<>();
    private final Map<String, List<String>> electors;


    public ElectionsWithoutDistrict(Map<String, List<String>> electors) {
        this.electors = electors;
    }

    @Override
    public void addCandidate(String candidate) {
        officialCandidates.add(candidate);
        candidates.add(candidate);
        votesWithoutDistricts.add(0);
    }


    @Override
    public void voteFor(String elector, String candidate, String electorDistrict) {
        if (candidates.contains(candidate)) {
            int index = candidates.indexOf(candidate);
            votesWithoutDistricts.set(index, votesWithoutDistricts.get(index) + 1);
        } else {
            // ouch
            candidates.add(candidate);
            // primitive code smell
            votesWithoutDistricts.add(1);
        }
    }

    @Override
    public Map<String, String> results() {
        Map<String, String> results = new HashMap<>();
        int nbVotes = 0;
        int nullVotes = 0;
        int blankVotes = 0;
        int nbValidVotes = 0;

        nbVotes = votesWithoutDistricts.stream().reduce(0, Integer::sum);
        for (int i = 0; i < officialCandidates.size(); i++) {
            int index = candidates.indexOf(officialCandidates.get(i));
            nbValidVotes += votesWithoutDistricts.get(index);
        }

        for (int i = 0; i < votesWithoutDistricts.size(); i++) {
            Float candidatResult = ((float) votesWithoutDistricts.get(i) * 100) / nbValidVotes;
            String candidate = candidates.get(i);
            if (officialCandidates.contains(candidate)) {
                results.put(candidate, String.format(Locale.FRENCH, "%.2f%%", candidatResult));
            } else {
                if (candidates.get(i).isEmpty()) {
                    blankVotes += votesWithoutDistricts.get(i);
                } else {
                    nullVotes += votesWithoutDistricts.get(i);
                }
            }
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
