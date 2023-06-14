package com.kata.elections;

import org.junit.jupiter.api.Test;

import javax.naming.NameAlreadyBoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class CandidateVotesTest {

    @Test
    void addCandidate() {
        CandidateVotes candidateVotes = new CandidateVotes();

        candidateVotes.addCandidate("Toto");

        assertThat(candidateVotes.getVotesFor("Toto")).isZero();
    }
    @Test
    void addCandidate_duplicate() {
        CandidateVotes candidateVotes = new CandidateVotes();

        candidateVotes.addCandidate("Toto");
        assertThatThrownBy(() -> candidateVotes.addCandidate("Toto"))
                .isInstanceOf(CandidateAlreadyExistsException.class)
                .hasMessage("Candidate Toto already exists");

    }

    @Test
    void addVote() {
        CandidateVotes candidateVotes = new CandidateVotes();

        candidateVotes.addCandidate("Toto");
        candidateVotes.addVote("Toto");
        candidateVotes.addVote("Toto");
        candidateVotes.addVote("Toto");

        assertThat(candidateVotes.getVotesFor("Toto")).isEqualTo(3);
    }

    @Test
    void getVotesFor() {
    }
}