package com.kata.elections;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CandidateVotesWithoutDistrictTest {

    @Test
    void addCandidate() {
        CandidateVotesInterface candidateVotesInterface = new CandidateVotesWithoutDistrict();

        candidateVotesInterface.addCandidate("Toto");

        assertThat(candidateVotesInterface.getVotesFor("Toto")).isZero();
    }
    @Test
    void addCandidate_duplicate() {
        CandidateVotesInterface candidateVotesInterface = new CandidateVotesWithoutDistrict();

        candidateVotesInterface.addCandidate("Toto");
        assertThatThrownBy(() -> candidateVotesInterface.addCandidate("Toto"))
                .isInstanceOf(CandidateAlreadyExistsException.class)
                .hasMessage("Candidate Toto already exists");

    }

    @Test
    void addVote() {
        CandidateVotesWithoutDistrict candidateVotesWithoutDistrict = new CandidateVotesWithoutDistrict();

        candidateVotesWithoutDistrict.addCandidate("Toto");

        candidateVotesWithoutDistrict.addVote("Toto");
        candidateVotesWithoutDistrict.addVote("Toto");
        candidateVotesWithoutDistrict.addVote("Toto");

        assertThat(candidateVotesWithoutDistrict.getVotesFor("Toto")).isEqualTo(3);
    }

    @Test
    void getVotesFor() {
    }
}