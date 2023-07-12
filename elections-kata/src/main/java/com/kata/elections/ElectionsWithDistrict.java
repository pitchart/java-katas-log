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
    private VotesPercentages votesPercentages = new VotesPercentages();
    private ElectionsResults electionsResults = new ElectionsResults();
    // compute votes
    private final VoteCountFactory voteCountFactory = new VoteCountFactory();


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
        VoteCountTo voteCountTo = voteCountFactory.getVoteCountToWithDistrict(electors, officialCandidates,candidateVotesByDistrict);

        // ResultsTO resultsTO = votesPercentages.computePercentage(voteCountTo);

        Map<String, Float> ratioByCandidate = new HashMap<>();
        for (int i = 0; i < officialCandidates.size(); i++) {
            Float ratioCandidate = ((float) voteCountTo.getScoresByCandidate().getOrDefault(officialCandidates.get(i),0)) / officialCandidates.size() * 100;
            ratioByCandidate.put(officialCandidates.get(i), ratioCandidate);
        }

        float blankResult = ((float) voteCountTo.getNbBlankVotes() * 100) / voteCountTo.getNbVotes();

        float nullResult = ((float) voteCountTo.getNullVotes() * 100) / voteCountTo.getNbVotes();

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        float abstentionResult = 100 - ((float) voteCountTo.getNbVotes() * 100 / voteCountTo.getNbElectors());

       // return electionsResults.displayResults(resultsTO);

        Map<String, String> results = new HashMap<>();
        for (int i = 0; i < officialCandidates.size(); i++) {
            results.put(officialCandidates.get(i), String.format(Locale.FRENCH, "%.2f%%", ratioByCandidate.get(officialCandidates.get(i))));
        }
        results.put("Blank", String.format(Locale.FRENCH, "%.2f%%", blankResult));
        results.put("Null", String.format(Locale.FRENCH, "%.2f%%", nullResult));
        results.put("Abstention", String.format(Locale.FRENCH, "%.2f%%", abstentionResult));
        return results;


    }

    private VoteCountTo getVoteCountTo() {
        int nbVotes = candidateVotesByDistrict.values().stream().mapToInt(CandidateVotes::getNbVotes).sum();
        int nbValidVotes = candidateVotesByDistrict.values().stream().mapToInt(candidateVotes -> candidateVotes.getNbValidVotes(officialCandidates)).sum();
        int blankVotes = candidateVotesByDistrict.values().stream().mapToInt(CandidateVotes::getNbBlankVotes).sum();
        int nullVotes = nbVotes - nbValidVotes - blankVotes;

        Map<String, Integer> officialCandidatesResult = new HashMap<>();
        candidateVotesByDistrict.values()
                .stream()
                .map(candidateVotes -> candidateVotes.getWinner(officialCandidates))
                .forEach(winner -> officialCandidatesResult.put(winner, officialCandidatesResult.getOrDefault(winner, 0) + 1));
        int nbElectors = electors.values().stream().map(List::size).reduce(0, Integer::sum);

        VoteCountTo voteCountTo = new VoteCountTo(nbVotes, nbValidVotes, blankVotes, nullVotes, nbElectors, officialCandidatesResult);
        return voteCountTo;
    }
}
