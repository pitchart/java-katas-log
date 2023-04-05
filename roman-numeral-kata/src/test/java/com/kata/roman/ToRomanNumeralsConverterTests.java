package com.kata.roman;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class ToRomanNumeralsConverterTests {
    private final ToRomanNumeralsConverter converter = new ToRomanNumeralsConverter();

    @ParameterizedTest
    @CsvSource({
            "1, I",
            "5, V",
            "10, X",
            "50, L",
            "100, C",
            "500, D",
            "1000, M"
    })
    void should_convert_simple_symbols(int arabic, String symbol){
        assertThat(converter.convert(arabic))
                .isEqualTo(symbol);
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
