package com.kata.elections;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class VoteCountFactory {

    public VoteCountTo getVoteCountTo(Map<String, List<String>> electors, List<String> officialCandidates, CandidateVotes candidateVotes) {

        int nbVotes = candidateVotes.getNbVotes();

        int nbValidVotes = candidateVotes.getNbValidVotes(officialCandidates);

        int nbBlankVotes = candidateVotes.getNbBlankVotes();

        int nullVotes = nbVotes - nbValidVotes - nbBlankVotes;

        int nbElectors = electors.values().stream().map(List::size).reduce(0, Integer::sum);

        Map<String, Integer> scoresByCandidate = candidateVotes.getScoresByCandidate(officialCandidates);

        return new VoteCountTo(nbVotes, nbValidVotes, nbBlankVotes, nullVotes, nbElectors, scoresByCandidate);
    }

}
