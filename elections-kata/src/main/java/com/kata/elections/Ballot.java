package com.kata.elections;

import org.junit.platform.commons.util.StringUtils;

/**
 * Property of project Cube. Copyright Decathlon 2023
 *
 * @author Théo Lhotte
 */
public class Ballot {


	private final String content;

	public Ballot(String s) {
		this.content = s;
	}

	public boolean isBlank() {
		return StringUtils.isBlank(content);
	}
}
