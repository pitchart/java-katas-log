package com.kata.elections;

import java.util.Map;

public class ResultsTO {
    private final float blankResult;
    private final float nullResult;
    private final float abstentionResult;
    private final Map<String, Float> resultsByCandidate;

    public ResultsTO(float blankResult, float nullResult, float abstentionResult, Map<String, Float> resultsByCandidate) {

        this.blankResult = blankResult;
        this.nullResult = nullResult;
        this.abstentionResult = abstentionResult;
        this.resultsByCandidate = resultsByCandidate;
    }

    public float getBlankResult() {
        return blankResult;
    }

    public float getNullResult() {
        return nullResult;
    }

    public float getAbstentionResult() {
        return abstentionResult;
    }

    public Map<String, Float> getResultsByCandidate() {
        return resultsByCandidate;
    }
}
