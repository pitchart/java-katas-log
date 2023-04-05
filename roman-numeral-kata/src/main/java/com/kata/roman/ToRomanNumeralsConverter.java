package com.kata.roman;

import java.util.Map;

public class ToRomanNumeralsConverter {

    private static final Map<Integer, String> ARABIC_TO_ROMAN_MAP = Map.of(1,"I",5,"V",10,"X",50,"L",100,"C",500,"D",1000,"M");

    public String convert(int arabic) {

        return ARABIC_TO_ROMAN_MAP.getOrDefault(arabic,"");

    }
}
