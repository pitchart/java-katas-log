package com.kata.elections;

/**
 * Property of project CUBE. Copyrights Decathlon 2023.
 *
 * @author EL MOUSSAOUI Tarik  - Cube-DC Team
 */
public enum VotesType {
    BLANK("Blank"),
    NULL("Null"), ABSTENTION("Abstention");

    public String getType() {
        return type;
    }

    private final String type;

    VotesType(String type) {
        this.type = type;
    }
}
