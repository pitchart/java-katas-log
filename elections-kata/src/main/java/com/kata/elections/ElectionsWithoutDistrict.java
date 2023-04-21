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
        ElectionResults electionResult = countVotes();

        return formatResults(electionResult);
    }

    private ElectionResults countVotes() {
        ElectionResults electionResult = new ElectionResults(officialCandidates, electoralListByDistrict, votesWithoutDistricts);

        electionResult.nbVotes = ballotBox.size();

        electionResult.nbValidVotes = (int)ballotBox.stream().filter(vote -> isValidVote(vote)).count();

        ballotBox.stream().filter(vote -> isValidVote(vote)).forEach(electionResult::addVote);
        electionResult.blankVotes = (int)ballotBox.stream().filter(Ballot::isBlank).count();
        electionResult.nullVotes = (int)ballotBox.stream().filter(vote -> isNullVote(vote)).count();
        return electionResult;
    }

    private Map<String, String> formatResults(ElectionResults electionResult) {
        Map<String, String> formattedResults = new HashMap<>();
        electionResult.getVotesByCandidate().forEach((candidate, vote) -> formattedResults.put(candidate, String.format(Locale.FRENCH, "%.2f%%", (float)(vote * 100) / electionResult.nbValidVotes)));

        float blankResult = ((float) electionResult.blankVotes * 100) / electionResult.nbVotes;
        formattedResults.put("Blank", String.format(Locale.FRENCH, "%.2f%%", blankResult));

        float nullResult = ((float) electionResult.nullVotes * 100) / electionResult.nbVotes;
        formattedResults.put("Null", String.format(Locale.FRENCH, "%.2f%%", nullResult));

        int nbElectors = electoralListByDistrict.values().stream().map(List::size).reduce(0, Integer::sum);
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        float abstentionResult = 100 - ((float) electionResult.nbVotes * 100 / nbElectors);
        formattedResults.put("Abstention", String.format(Locale.FRENCH, "%.2f%%", abstentionResult));
        return formattedResults;
    }

    private boolean isValidVote(Ballot vote) {
        return officialCandidates.contains(vote.content());
    }

    private boolean isNullVote(Ballot vote) {
        return !(vote.isBlank() || isValidVote(vote));
    }

    private boolean isOfficialCandidate(String candidate) {
        return officialCandidates.contains(candidate);
    }

}
