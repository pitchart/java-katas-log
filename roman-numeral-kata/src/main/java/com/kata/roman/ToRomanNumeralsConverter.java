package com.kata.roman;

public class ToRomanNumeralsConverter {
    public String convert(int arabic) {
        if(arabic == 5){
            return "V";
        }
        if (arabic == 1) return "I";
        return "";
    }
}
