package com.kata.elections;

import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 */
public class VotesPercentages {
    public ResultsTO computePercentage(VoteCountTo voteCountTo) {
        // Compute percent
        Map<String, Float> resultsByCandidate = voteCountTo.getScoresByCandidate().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> (float) e.getValue() * 100 / voteCountTo.getNbValidVotes()));

        float blankResult = ((float) voteCountTo.getNbBlankVotes() * 100) / voteCountTo.getNbVotes();
        float nullResult = ((float) voteCountTo.getNullVotes() * 100) / voteCountTo.getNbVotes();
        float abstentionResult = 100 - ((float) voteCountTo.getNbVotes() * 100 / voteCountTo.getNbElectors());
        return new ResultsTO(blankResult, nullResult, abstentionResult, resultsByCandidate);
    }
}
