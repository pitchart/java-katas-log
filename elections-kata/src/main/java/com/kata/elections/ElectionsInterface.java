package com.kata.elections;

import java.util.Map;

public interface ElectionsInterface {
    void addCandidate(String candidate);

    // not used elector
    void voteFor(String elector, String candidate, String electorDistrict);

    Map<String, String> results();
}
