package com.kata.elections;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Property of project CUBE. Copyrights Decathlon 2023.
 *
 * @author EL MOUSSAOUI Tarik  - Cube-DC Team
 */
public class CandidateVotesWithDistrict implements CandidateVotesInterface {

    private final Map<String, CandidateVotesInterface> candidateVotesByDistrict;

    public CandidateVotesWithDistrict(Map<String, CandidateVotesInterface> candidateVotesByDistrict) {
        this.candidateVotesByDistrict = candidateVotesByDistrict;
    }

    @Override
    public Integer getNbValidVotes(List<String> officialCandidates) {
        return candidateVotesByDistrict.values().stream().mapToInt(candidateVotes -> candidateVotes.getNbValidVotes(officialCandidates)).sum();
    }

    @Override
    public Integer getNbVotes() {
       return candidateVotesByDistrict.values().stream().mapToInt(CandidateVotesInterface::getNbVotes).sum();
    }

    @Override
    public void addCandidate(String candidate) {

    }

    @Override
    public void addVote(String candidate, String electorDistrict) {
        if (!candidateVotesByDistrict.containsKey(electorDistrict)) {
            return;
        }
        candidateVotesByDistrict.get(electorDistrict).addVote(candidate, electorDistrict);
    }

    @Override
    public int getVotesFor(String candidate) {
        return 0;
    }

    @Override
    public int getNbBlankVotes() {
        return candidateVotesByDistrict.values().stream().mapToInt(CandidateVotesInterface::getNbBlankVotes).sum();
    }

    @Override
    public Map<String, Integer> getScoresByCandidate(List<String> officialCandidates) {
        Map<String, Integer> officialCandidatesResult = officialCandidates.stream().collect(Collectors.toMap(candidate -> candidate, candidate->0));

        candidateVotesByDistrict.values()
                .stream()
                .map(candidateVotes -> candidateVotes.getWinner(officialCandidates))
                .forEach(winner -> officialCandidatesResult.put(winner, officialCandidatesResult.getOrDefault(winner, 0) + 1));

        return officialCandidatesResult;
    }

    @Override
    public String getWinner(List<String> officialCandidates) {
        return null;
    }
}
