package com.kata.elections;

import java.util.Collections;
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
public class CandidateVotesWithoutDistrict implements CandidateVotesInterface {

    private final Map<String, Integer> votesByCandidate = new HashMap<>();

    @Override
    public Integer getNbValidVotes(List<String> officialCandidates) {
        return votesByCandidate.entrySet().stream()
                .filter(e1 -> officialCandidates.contains(e1.getKey()))
                .map(Map.Entry::getValue)
                .reduce(0, Integer::sum);
    }

    @Override
    public Integer getNbVotes() {
        return votesByCandidate.values().stream().reduce(0, Integer::sum);
    }

    @Override
    public void addCandidate(String candidate) {
        if(votesByCandidate.containsKey(candidate)){
            throw CandidateAlreadyExistsException.createCandidateAlreadyExistsException(candidate);
        }
        votesByCandidate.put(candidate, 0);
    }

    @Override
    public void addVote(String candidate, String electorDistrict) {
        votesByCandidate.put(candidate, getVotesFor(candidate) + 1);
    }

    public void addVote(String candidate) {
        addVote(candidate, "");
    }

    @Override
    public int getVotesFor(String candidate) {
        return votesByCandidate.getOrDefault(candidate,0);
    }

    @Override
    public int getNbBlankVotes() {
        return getVotesFor("");
    }

    @Override
    public Map<String, Integer> getScoresByCandidate(List<String> officialCandidates) {
        return officialCandidates.stream().collect(Collectors.toMap(Function.identity(), this::getVotesFor));
    }

    @Override
    public String getWinner(List<String> officialCandidates) {
        return Collections.max(getScoresByCandidate(officialCandidates).entrySet(), Map.Entry.comparingByValue()).getKey();
    }
}
