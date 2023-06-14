package com.kata.elections;

import java.text.MessageFormat;

public class CandidateAlreadyExistsException extends RuntimeException {
    private CandidateAlreadyExistsException(String candidate) {
        super(MessageFormat.format("Candidate {0} already exists", candidate));
    }

    public static CandidateAlreadyExistsException createCandidateAlreadyExistsException(String candidate){
        return new CandidateAlreadyExistsException(candidate);
    }
}
