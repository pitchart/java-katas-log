package com.kata.elections;

import java.text.DecimalFormat;
import java.util.*;

public class Elections implements ElectionsInterface {

    private final ElectionsWithoutDistrict electionsWithoutDistrict;
    private final ElectionsWithDistrict electionsWithDistrict;

    private boolean withDistrict;

    public Elections(Map<String, List<String>> electors, boolean withDistrict) {
        this.withDistrict = withDistrict;

        this.electionsWithoutDistrict = new ElectionsWithoutDistrict(electors);
        this.electionsWithDistrict = new ElectionsWithDistrict(electors);
    }

    @Override
    public void addCandidate(String candidate) {
        this.electionsWithoutDistrict.addCandidate(candidate);
        this.electionsWithDistrict.addCandidate(candidate);
    }

    // not used elector
    @Override
    public void voteFor(String elector, String candidate, String electorDistrict) {
        // inverse test for readability
        if (!withDistrict) {
           electionsWithoutDistrict.voteFor(elector, candidate, electorDistrict);
        } else {
            electionsWithDistrict.voteFor(elector, candidate, electorDistrict);
        }
    }

    @Override
    public Map<String, String> results() {
        if (!withDistrict) {
          return this.electionsWithoutDistrict.results();
        }
        return electionsWithDistrict.results();
    }
}
