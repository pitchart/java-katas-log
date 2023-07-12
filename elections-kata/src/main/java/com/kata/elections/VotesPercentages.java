package com.kata.elections;

import java.text.DecimalFormat;
import java.util.HashMap;
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
                .collect(Collectors.toMap(Map.Entry::getKey, e -> getPercentage(e.getValue(),voteCountTo.getNbValidVotes())));

        float blankResult = getPercentage(voteCountTo.getNbBlankVotes(), voteCountTo.getNbVotes());
        float nullResult = getPercentage(voteCountTo.getNullVotes(), voteCountTo.getNbVotes());
        float abstentionResult = 100 - getPercentage(voteCountTo.getNbVotes(),voteCountTo.getNbElectors());

        return new ResultsTO(blankResult, nullResult, abstentionResult, resultsByCandidate);
    }

    private static float getPercentage(int subTotal, int total) {
        return ((float)subTotal * 100) / total;
    }

    public ResultsTO computePercentageWithDistrict(VoteCountTo voteCountTo) {

        Map<String, Float> ratioByCandidate = new HashMap<>();
        voteCountTo.getScoresByCandidate().forEach((candidate, score) -> {
            Float ratioCandidate = getPercentage(score, voteCountTo.getNbDistricts());
            ratioByCandidate.put(candidate, ratioCandidate);
        });

        float blankResult = getPercentage(voteCountTo.getNbBlankVotes(), voteCountTo.getNbVotes());
        float nullResult = getPercentage(voteCountTo.getNullVotes(), voteCountTo.getNbVotes());
        float abstentionResult = 100 - getPercentage(voteCountTo.getNbVotes(),voteCountTo.getNbElectors());

        return new ResultsTO(blankResult, nullResult, abstentionResult, ratioByCandidate);
    }
}
