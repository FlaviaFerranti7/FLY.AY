package com.flam.flyay.util;

public enum CategoryEnum {
    WELLNESS("WELLNESS", "#9400D3"),
    FINANCES("FINANCES", "#FFCF05"),
    FREE_TIME("FREE_TIME", "#FF3612"),
    STUDY("STUDY", "#0C49B6"),
    FESTIVITY("FESTIVITY", "#FF8100");

    public String name;
    public String color;

    CategoryEnum(String name, String color) {
        this.name = name;
        this.color = color;
    }
}
