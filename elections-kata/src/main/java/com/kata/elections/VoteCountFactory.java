package com.kata.elections;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class VoteCountFactory {

    public VoteCountTo getVoteCountTo(Map<String, List<String>> electors, List<String> officialCandidates, CandidateVotesInterface candidateVotesByDistrict) {

        int nbVotes =  candidateVotesByDistrict.getNbVotes();
        int nbValidVotes = candidateVotesByDistrict.getNbValidVotes(officialCandidates);
        int blankVotes = candidateVotesByDistrict.getNbBlankVotes();
        int nullVotes = nbVotes - nbValidVotes - blankVotes;

        Map<String, Integer> officialCandidatesResult = candidateVotesByDistrict.getScoresByCandidate(officialCandidates);

        int nbElectors = electors.values().stream().map(List::size).reduce(0, Integer::sum);

        VoteCountTo voteCountTo = new VoteCountTo(nbVotes, nbValidVotes, blankVotes, nullVotes, nbElectors, officialCandidatesResult, electors.size());
        return voteCountTo;
    }
}
