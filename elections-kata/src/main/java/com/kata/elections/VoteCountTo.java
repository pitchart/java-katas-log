package com.kata.elections;

import java.util.Map;

/**
 * Property of project CUBE. Copyrights Decathlon 2023.
 *
 * @author EL MOUSSAOUI Tarik  - Cube-DC Team
 */
public class VoteCountTo {
    private final int nbDistricts;
    private final int nbVotes;
    private final int nbValidVotes;
    private final int nbBlankVotes;
    private final int nullVotes;
    private final int nbElectors;
    private final Map<String, Integer> scoresByCandidate;
    public VoteCountTo(Integer nbVotes, int nbValidVotes, int nbBlankVotes, int nullVotes, int nbElectors, Map<String, Integer> scoresByCandidate, int nbDistricts) {
        this.nbVotes = nbVotes;
        this.nbValidVotes = nbValidVotes;
        this.nbBlankVotes = nbBlankVotes;
        this.nullVotes = nullVotes;
        this.nbElectors = nbElectors;
        this.scoresByCandidate = scoresByCandidate;
        this.nbDistricts = nbDistricts;
    }

    public int getNbDistricts() {
        return nbDistricts;
    }

    public Integer getNbVotes() {
        return nbVotes;
    }

    public int getNbValidVotes() {
        return nbValidVotes;
    }

    public int getNbBlankVotes() {
        return nbBlankVotes;
    }

    public int getNullVotes() {
        return nullVotes;
    }

    public int getNbElectors() {
        return nbElectors;
    }

    public Map<String, Integer> getScoresByCandidate() {
        return scoresByCandidate;
    }
}
