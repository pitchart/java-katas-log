package com.kata.elections;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

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

        Map<String, Integer> scoresByCandidate = candidateVotes.getVotesByCandidate().entrySet().stream()
                .filter(e21 -> officialCandidates.contains(e21.getKey()))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));

        return new VoteCountTo(nbVotes, nbValidVotes, nbBlankVotes, nullVotes, nbElectors, scoresByCandidate);
    }

}
