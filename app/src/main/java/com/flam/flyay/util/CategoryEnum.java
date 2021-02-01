package com.flam.flyay.util;

public enum CategoryEnum {
    WELLNESS("WELLNESS", "#9966CC"),
    FINANCES("FINANCES", "#F7D917"),
    FREE_TIME("FREE_TIME", "#E06060"),
    STUDY("STUDY", "#2271B3"),
    FESTIVITY("FESTIVITY", "#FFA500");

    public String name;
    public String color;

    CategoryEnum(String name, String color) {
        this.name = name;
        this.color = color;
    }
}
