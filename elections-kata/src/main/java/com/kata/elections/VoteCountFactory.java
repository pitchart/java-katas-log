package com.kata.elections;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 *
 */
public class VoteCountFactory {
     VoteCountTo getVoteCountTo(Map<String, List<String>> electors, List<String> officialCandidates, Map<String, Integer> votesByCandidate) {

        int nbVotes = votesByCandidate.values().stream().reduce(0, Integer::sum);

        int nbValidVotes = votesByCandidate.entrySet().stream()
                .filter(e1 -> officialCandidates.contains(e1.getKey()))
                .map(Map.Entry::getValue)
                .reduce(0, Integer::sum);
        int nbBlankVotes = votesByCandidate.getOrDefault("", 0);

        int nullVotes = nbVotes - votesByCandidate.entrySet().stream()
                .filter(e11 -> officialCandidates.contains(e11.getKey()))
                .map(Map.Entry::getValue)
                .reduce(0, Integer::sum) - votesByCandidate.getOrDefault("", 0);

        int nbElectors = electors.values().stream().map(List::size).reduce(0, Integer::sum);

        Map<String, Integer> scoresByCandidate = votesByCandidate.entrySet().stream()
                .filter(e21 -> officialCandidates.contains(e21.getKey()))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
        return new VoteCountTo(nbVotes, nbValidVotes, nbBlankVotes, nullVotes, nbElectors, scoresByCandidate);
    }
}
