package com.kata.elections;

import javax.naming.NameAlreadyBoundException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Property of project CUBE. Copyrights Decathlon 2023.
 *
 * @author EL MOUSSAOUI Tarik  - Cube-DC Team
 */
public class CandidateVotes {

    private final Map<String, Integer> votesByCandidate = new HashMap<>();

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

    //TODO FIX ME
    public Map<String, Integer> getVotesByCandidate() {
        return votesByCandidate;
    }
}
