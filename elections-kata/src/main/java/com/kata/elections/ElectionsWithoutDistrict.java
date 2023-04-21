package com.kata.elections;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ElectionsWithoutDistrict {

    List<String> votes = new ArrayList<>();
    List<String> officialCandidates = new ArrayList<>();
    ArrayList<Integer> votesWithoutDistricts = new ArrayList<>();
    private Map<String, List<String>> electoralListByDistrict;
    private List<Ballot> ballotBox = new ArrayList<>();

    public ElectionsWithoutDistrict(Map<String, List<String>> electoralListByDistrict) {
        this.electoralListByDistrict = electoralListByDistrict;
    }

    public void addCandidate(String candidate) {
        officialCandidates.add(candidate);
        votes.add(candidate);
        votesWithoutDistricts.add(0);
    }

    public void voteFor(String elector, String candidate, String electorDistrict) {
        Ballot ballot = new Ballot(candidate);
        this.ballotBox.add(ballot);
        if (votes.contains(candidate)) {
            int index = votes.indexOf(candidate);
            votesWithoutDistricts.set(index, votesWithoutDistricts.get(index) + 1);
        } else {
            votes.add(candidate);
            votesWithoutDistricts.add(1);
        }
    }

    public Map<String, String> results() {
        ElectionResults electionResult = new ElectionResults(officialCandidates, electoralListByDistrict, votesWithoutDistricts);
        Map<String, String> formattedResults = new HashMap<>();
        Integer nbVotes = 0;
        Integer nullVotes = 0;
        int nbValidVotes = 0;

        nbVotes = ballotBox.size();
        for (int i = 0; i < officialCandidates.size(); i++) {
            int index = votes.indexOf(officialCandidates.get(i));
            nbValidVotes += votesWithoutDistricts.get(index);
            electionResult.nbValidVotes += votesWithoutDistricts.get(index);
        }

        for (int i = 0; i < votesWithoutDistricts.size(); i++) {
            Float candidatResult = ((float) votesWithoutDistricts.get(i) * 100) / nbValidVotes;
            String candidate = votes.get(i);
            if (isOfficialCandidate(candidate)) {
                formattedResults.put(candidate, String.format(Locale.FRENCH, "%.2f%%", candidatResult));
                electionResult.setCandidateScore(candidate, votesWithoutDistricts.get(i));
            } else {
                if (!voteIsBlank(i)) {
                    nullVotes += votesWithoutDistricts.get(i);
                    electionResult.nullVotes += votesWithoutDistricts.get(i);
                }
            }
        }

        electionResult.blankVotes = (int)ballotBox.stream().filter(Ballot::isBlank).count();

        float blankResult = ((float) electionResult.blankVotes * 100) / nbVotes;
        formattedResults.put("Blank", String.format(Locale.FRENCH, "%.2f%%", blankResult));

        float nullResult = ((float) nullVotes * 100) / nbVotes;
        formattedResults.put("Null", String.format(Locale.FRENCH, "%.2f%%", nullResult));

        int nbElectors = electoralListByDistrict.values().stream().map(List::size).reduce(0, Integer::sum);
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        float abstentionResult = 100 - ((float) nbVotes * 100 / nbElectors);
        formattedResults.put("Abstention", String.format(Locale.FRENCH, "%.2f%%", abstentionResult));

        return formattedResults;
    }

    private boolean voteIsBlank(int i) {
        return votes.get(i).isEmpty();
    }

    private boolean isOfficialCandidate(String candidate) {
        return officialCandidates.contains(candidate);
    }

}
