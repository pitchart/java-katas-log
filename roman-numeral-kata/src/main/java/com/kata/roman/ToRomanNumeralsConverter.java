package com.kata.roman;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class ToRomanNumeralsConverter {

    private static final TreeMap<Integer,String> ARABIC_TO_ROMAN_MAP = new TreeMap<>(Map.of(
            1000, "M",
            500, "D",
            100, "C",
            50, "L",
            10, "X",
            5, "V",
            1, "I"
    ));

    public String convert(int arabic) {
        for(Integer key : ARABIC_TO_ROMAN_MAP.descendingKeySet()){
            if (arabic >= key) return ARABIC_TO_ROMAN_MAP.get(key) + convert(arabic - key);
        }

        return ARABIC_TO_ROMAN_MAP.getOrDefault(arabic, "");
    }
}
