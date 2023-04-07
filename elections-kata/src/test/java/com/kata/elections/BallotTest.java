package com.kata.elections;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Property of project Cube. Copyright Decathlon 2023
 *
 * @author Théo Lhotte
 */
public class BallotTest {

	@Test
	void should_be_blank_when_has_no_content(){
		Ballot blankBallot= new Ballot("");

		assertThat(blankBallot.isBlank()).isTrue();

	}

	@Test
	void should_not_be_blank_when_has_content(){
		Ballot ballotWithContent= new Ballot("Barack Obama");

		assertThat(ballotWithContent.isBlank()).isFalse();

	}


}
