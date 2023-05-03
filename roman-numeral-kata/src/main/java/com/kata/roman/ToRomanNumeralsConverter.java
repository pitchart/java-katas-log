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
            1, "I",
            0, ""
    ));


    public String convert(int arabic) {


//        ARABIC_TO_ROMAN_MAP.entrySet().stream()
//                .collect(Collectors.toList());



        if(arabic == 0) return "";

        for(Integer key : ARABIC_TO_ROMAN_MAP.keySet()){
            if (arabic >= key) return ARABIC_TO_ROMAN_MAP.get(key) + convert(arabic - key);
        }

        if (arabic >= 1000) return ARABIC_TO_ROMAN_MAP.get(1000) + convert(arabic - 1000);
        if (arabic >= 500) return ARABIC_TO_ROMAN_MAP.get(500) + convert(arabic - 500);
        if (arabic >= 100) return ARABIC_TO_ROMAN_MAP.get(100) + convert(arabic - 100);
        if (arabic >= 50) return ARABIC_TO_ROMAN_MAP.get(50) + convert(arabic - 50);
        if (arabic >= 10) return ARABIC_TO_ROMAN_MAP.get(10) + convert(arabic - 10);
        if (arabic >= 5) return ARABIC_TO_ROMAN_MAP.get(5) + convert(arabic - 5);
        if (arabic >= 1) return ARABIC_TO_ROMAN_MAP.get(1);

        return ARABIC_TO_ROMAN_MAP.getOrDefault(arabic, "");

    }
}
