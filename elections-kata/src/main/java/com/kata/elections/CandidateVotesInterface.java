package com.kata.elections;

import java.util.List;
import java.util.Map;

public interface CandidateVotesInterface {
    Integer getNbValidVotes(List<String> officialCandidates);

    Integer getNbVotes();

    void addCandidate(String candidate);

    void addVote(String candidate, String electorDistrict);

    int getVotesFor(String candidate);

    int getNbBlankVotes();

    Map<String, Integer> getScoresByCandidate(List<String> officialCandidates);

    String getWinner(List<String> officialCandidates);
}
