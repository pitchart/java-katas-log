package com.kata.elections;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Property of project CUBE. Copyrights Decathlon 2023.
 *
 * @author EL MOUSSAOUI Tarik  - Cube-DC Team
 */
public class CandidateVotes {

    private final Map<String, Integer> votesByCandidate = new HashMap<>();

    public Integer getNbValidVotes(List<String> officialCandidates) {
        return votesByCandidate.entrySet().stream()
                .filter(e1 -> officialCandidates.contains(e1.getKey()))
                .map(Map.Entry::getValue)
                .reduce(0, Integer::sum);
    }

    public Integer getNbVotes() {
        return votesByCandidate.values().stream().reduce(0, Integer::sum);
    }

    public void addCandidate(String candidate) {
        if(votesByCandidate.containsKey(candidate)){
            throw CandidateAlreadyExistsException.createCandidateAlreadyExistsException(candidate);
        }
        votesByCandidate.put(candidate, 0);
    }

    public void addVote(String candidate) {
        votesByCandidate.put(candidate, getVotesFor(candidate) + 1);
    }

    public int getVotesFor(String candidate) {
        return votesByCandidate.getOrDefault(candidate,0);
    }

    public int getNbBlankVotes() {
        return getVotesFor("");
    }

    public Map<String, Integer> getScoresByCandidate(List<String> officialCandidates) {
        return officialCandidates.stream().collect(Collectors.toMap(Function.identity(), this::getVotesFor));
    }
}
