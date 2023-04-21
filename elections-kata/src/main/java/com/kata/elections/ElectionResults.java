package com.kata.elections;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Property of project Cube. Copyright Decathlon 2023
 *
 * @author Théo Lhotte
 */
public class ElectionResults {

	private final Map<String, Integer> votesByCandidate;
	private final Integer nbElectors;
	public Integer nbVotes;
	public Integer nbValidVotes = 0;
	public Integer nullVotes = 0;
	public Integer blankVotes = 0;

	public ElectionResults(List<String> candidates, Map<String, List<String>> electoralListByDistrict, ArrayList<Integer> votesWithoutDistricts) {
		this.votesByCandidate = candidates.stream().collect(Collectors.toMap(candidate -> candidate, candidate -> 0));
		this.nbElectors = electoralListByDistrict.values().stream().map(List::size).reduce(0, Integer::sum);
		this.nbVotes = votesWithoutDistricts.stream().reduce(0, Integer::sum);

	}

	public void addVote(Ballot ballot) {
		votesByCandidate.put(ballot.content(), votesByCandidate.get(ballot.content()) + 1);
	}

	public Map<String, Integer> getVotesByCandidate() {
		return votesByCandidate;
	}

}
