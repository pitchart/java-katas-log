package com.kata.elections;

import java.util.Map;

/**
 * Property of project CUBE. Copyrights Decathlon 2023.
 *
 * @author EL MOUSSAOUI Tarik  - Cube-DC Team
 */
public class ResultTO {
    public Integer nbVotes;
    public int nbValidVotes;
    public int nbBlankVotes;
    public int nullVotes;
    public int nbElectors;
    public Map<String, Integer> scoresByCandidate;
}
