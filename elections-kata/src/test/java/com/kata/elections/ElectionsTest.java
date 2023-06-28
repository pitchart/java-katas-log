package com.kata.elections;

import org.approvaltests.Approvals;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

class ElectionsTest {
    @Test
    void electionWithoutDistricts() {
        Map<String, List<String>> electors = Map.of(
                "District 1", Arrays.asList("Bob", "Anna", "Jess", "July"),
                "District 2", Arrays.asList("Jerry", "Simon"),
                "District 3", Arrays.asList("Johnny", "Matt", "Carole")
        );

        List<String> candidates = List.of("Michel", "Jerry", "Johnny");
        Elections elections = new ElectionsWithoutDistrict(electors, candidates);

        elections.voteFor("Bob", "Jerry", "District 1");
        elections.voteFor("Jerry", "Jerry", "District 2");
        elections.voteFor("Anna", "Johnny", "District 1");
        elections.voteFor("Johnny", "Johnny", "District 3");
        elections.voteFor("Matt", "Donald", "District 3");
        elections.voteFor("Jess", "Joe", "District 1");
        elections.voteFor("Simon", "", "District 2");
        elections.voteFor("Carole", "", "District 3");

        var results = elections.results();

        // THEN
        Approvals.verify(results);
    }

    @Test
    void electionWithDistricts() {
        Map<String, List<String>> electors = Map.of(
                "District 1", Arrays.asList("Bob", "Anna", "Jess", "July"),
                "District 2", Arrays.asList("Jerry", "Simon"),
                "District 3", Arrays.asList("Johnny", "Matt", "Carole")
        );

        Elections elections = new ElectionsWithDistrict(electors);
        elections.addCandidate("Michel");
        elections.addCandidate("Jerry");
        elections.addCandidate("Johnny");

        elections.voteFor("Bob", "Jerry", "District 1");
        elections.voteFor("Jerry", "Jerry", "District 2");
        elections.voteFor("Anna", "Johnny", "District 1");
        elections.voteFor("Johnny", "Johnny", "District 3");
        elections.voteFor("Matt", "Donald", "District 3");
        elections.voteFor("Jess", "Joe", "District 1");
        elections.voteFor("July", "Jerry", "District 1");
        elections.voteFor("Simon", "", "District 2");
        elections.voteFor("Carole", "", "District 3");

        var results = elections.results();

        // THEN
        Approvals.verify(results);
    }

    @Test
    void electionWithDistrict_should(){
        Map<String, List<String>> electors = Map.of(
                "District 4", Arrays.asList("Bob", "Anna", "Jess", "July"));

        Elections elections = new ElectionsWithDistrict(electors);
        elections.addCandidate("Renny");
        elections.voteFor("Bob", "Renny", "District 4");

        var results = elections.results();
        assertTrue(results.containsKey("Renny"));
        assertEquals("100,00%", results.get("Renny"));
    }

    @Test
    void electionWithDistricts_with_more_districts_than_candidates() {
        Map<String, List<String>> electors = Map.of(
                "District 1", Arrays.asList("Bob", "Anna", "Jess", "July"),
                "District 2", Arrays.asList("Jerry", "Simon"),
                "District 3", Arrays.asList("Johnny", "Matt", "Carole")
        );

        Elections elections = new ElectionsWithDistrict(electors);
        elections.addCandidate("Johnny");
        elections.addCandidate("Jerry");

        elections.voteFor("Bob", "Jerry", "District 1");
        elections.voteFor("Jerry", "Jerry", "District 2");
        elections.voteFor("Anna", "Johnny", "District 1");
        elections.voteFor("Johnny", "Johnny", "District 3");
        elections.voteFor("Matt", "Donald", "District 3");
        elections.voteFor("Jess", "Joe", "District 1");
        elections.voteFor("July", "Jerry", "District 1");
        elections.voteFor("Simon", "", "District 2");
        elections.voteFor("Carole", "", "District 3");

        var results = elections.results();

        // THEN
        Approvals.verify(results);
    }
}
