package com.kata.elections;


import java.util.*;
import java.util.stream.Collectors;

/**
 * Property of project CUBE. Copyright Decathlon 2023.
 *
 * @author Cube-DC team {@literal <cubegetdreexlille@decathlon.net>}
 */
public class ElectionsWithDistrict implements Elections {
    // compute votes
    private final VoteCountFactory voteCountWithDistrictFactory = new VoteCountFactory();
    private final List<String> officialCandidates = new ArrayList<>();
    private final Map<String, List<String>> electorsByDistrict;
    private final Map<String, CandidateVotesInterface> candidateVotesByDistrict;
    private final VotesPercentages votesPercentages = new VotesPercentages();
    private final ElectionsResults electionsResults = new ElectionsResults();
    private final CandidateVotesWithDistrict candidatesVotes;


    public ElectionsWithDistrict(Map<String, List<String>> electors) {
        this.electorsByDistrict = electors;
        candidateVotesByDistrict = electors.keySet().stream().collect(Collectors.toMap(district -> district, district -> new CandidateVotesWithoutDistrict()));
        candidatesVotes = new CandidateVotesWithDistrict(candidateVotesByDistrict);
    }

    @Override
    public void addCandidate(String candidate) {
        officialCandidates.add(candidate);
    }

    @Override
    public void voteFor(String elector, String candidate, String electorDistrict) {
        candidatesVotes.addVote(candidate, electorDistrict);
    }

    @Override
    public Map<String, String> results() {
        VoteCountTo voteCountTo = voteCountWithDistrictFactory.getVoteCountTo(electorsByDistrict, officialCandidates, candidatesVotes);
        ResultsTO resultsTO = votesPercentages.computePercentageWithDistrict(voteCountTo);
        return electionsResults.displayResults(resultsTO);


    }
}
