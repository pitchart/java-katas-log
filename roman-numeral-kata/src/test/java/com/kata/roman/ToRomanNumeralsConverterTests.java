package com.kata.roman;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ToRomanNumeralsConverterTests {
    private final ToRomanNumeralsConverter converter = new ToRomanNumeralsConverter();

    /*
    Test "single symbols"
    I: 1
    V: 5
    X: 10
    L: 50
    C: 100
    D: 500
    M: 1000
     */

    @Test
    void should_convert_simple_symbols() {
        assertThat(converter.convert(1))
                .isEqualTo("I");
    }

    /*
    Test "simple concatenation"
    6: VI
    11: XI
    1001: MI
    1551: MDLI
    1666: MDCLXVI
     */

    /*
    Test "repeating symbols"
    2: II
    3: III
    20: XX
    30: XXX
    33: XXXIII
    3888: MMMDCCCLXXXVIII
     */

    /*
    Test "substract symbols"
    4: IV
    9: IX
    40: XL
    90: XC
    400: CD
    900: CM
    1903: MCMIII
     */

    @Test
    void should_be_empty_for_zero() {
        assertThat(converter.convert(0))
                .isBlank();
    }
}
