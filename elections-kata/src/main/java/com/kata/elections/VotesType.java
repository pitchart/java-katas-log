package com.kata.elections;

import java.util.function.Function;

/**
 * Property of project CUBE. Copyrights Decathlon 2023.
 *
 * @author EL MOUSSAOUI Tarik  - Cube-DC Team
 */
public enum VotesType {
    BLANK("Blank", ResultsTO::getBlankResult),
    NULL("Null", ResultsTO::getNullResult),
    ABSTENTION("Abstention", ResultsTO::getAbstentionResult);


    private final String type;
    private final Function<ResultsTO, Float> function;

    VotesType(String type, Function<ResultsTO,Float> function ) {
        this.type = type;
        this.function = function;
    }

    public Function<ResultsTO, Float> getFunction() {
        return function;
    }

    public String getType() {
        return type;
    }
}
