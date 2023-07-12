package com.kata.elections;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ElectionsWithoutDistrict implements Elections {
    List<String> officialCandidates = new ArrayList<>();
    private final Map<String, List<String>> electors;
    private final CandidateVotesInterface candidateVotesInterface = new CandidateVotesWithoutDistrict();
    // compute votes
    private final VoteCountFactory voteCountWithDistrictFactory = new VoteCountFactory();
    private final VotesPercentages votesPercentages = new VotesPercentages();
    private final ElectionsResults electionsResults = new ElectionsResults();



    public ElectionsWithoutDistrict(Map<String, List<String>> electors, List<String> candidates) {
        this.electors = electors;
        candidates.forEach(this::addCandidate);
    }

    @Override
    public void addCandidate(String candidate) {
        officialCandidates.add(candidate);
    }


    @Override
    public void voteFor(String elector, String candidate, String electorDistrict) {
        candidateVotesInterface.addVote(candidate, electorDistrict);
    }

    @Override
    public Map<String, String> results() {
        VoteCountTo voteCountTo = voteCountWithDistrictFactory.getVoteCountTo(electors, officialCandidates, candidateVotesInterface);
        ResultsTO resultsTO = votesPercentages.computePercentage(voteCountTo);
        return electionsResults.displayResults(resultsTO);
    }

}
