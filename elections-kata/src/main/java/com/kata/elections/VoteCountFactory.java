package com.kata.elections;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 */
public class VoteCountFactory {

    public VoteCountTo getVoteCountToWithoutDistrict(Map<String, List<String>> electors, List<String> officialCandidates, CandidateVotes candidateVotes) {

        int nbVotes = candidateVotes.getNbVotes();

        int nbValidVotes = candidateVotes.getNbValidVotes(officialCandidates);

        int nbBlankVotes = candidateVotes.getNbBlankVotes();

        int nullVotes = nbVotes - nbValidVotes - nbBlankVotes;

        int nbElectors = electors.values().stream().map(List::size).reduce(0, Integer::sum);

        Map<String, Integer> scoresByCandidate = candidateVotes.getScoresByCandidate(officialCandidates);

        return new VoteCountTo(nbVotes, nbValidVotes, nbBlankVotes, nullVotes, nbElectors, scoresByCandidate);
    }

    public VoteCountTo getVoteCountToWithDistrict(Map<String, List<String>> electors, List<String> officialCandidates, Map<String, CandidateVotes> candidateVotesByDistrict) {
        int nbVotes = candidateVotesByDistrict.values().stream().mapToInt(CandidateVotes::getNbVotes).sum();
        int nbValidVotes = candidateVotesByDistrict.values().stream().mapToInt(candidateVotes -> candidateVotes.getNbValidVotes(officialCandidates)).sum();
        int blankVotes = candidateVotesByDistrict.values().stream().mapToInt(CandidateVotes::getNbBlankVotes).sum();
        int nullVotes = nbVotes - nbValidVotes - blankVotes;

      /*  Map<String, Float> ratioByCandidate = new HashMap<>();
        for (int i = 0; i < officialCandidates.size(); i++) {
            Float ratioCandidate = ((float) voteCountTo.getScoresByCandidate().getOrDefault(officialCandidates.get(i),0)) / officialCandidates.size() * 100;
            ratioByCandidate.put(officialCandidates.get(i), ratioCandidate);
        }
*/
        Map<String, Integer> officialCandidatesResult = officialCandidates.stream().collect(Collectors.toMap(candidate -> candidate, candidate->0));
        // itérer sur officialCandidates
        candidateVotesByDistrict.values()
                .stream()
                .map(candidateVotes -> candidateVotes.getWinner(officialCandidates))
                .forEach(winner -> officialCandidatesResult.put(winner, officialCandidatesResult.getOrDefault(winner, 0) + 1));
        int nbElectors = electors.values().stream().map(List::size).reduce(0, Integer::sum);

        VoteCountTo voteCountTo = new VoteCountTo(nbVotes, nbValidVotes, blankVotes, nullVotes, nbElectors, officialCandidatesResult);
        return voteCountTo;
    }
}
