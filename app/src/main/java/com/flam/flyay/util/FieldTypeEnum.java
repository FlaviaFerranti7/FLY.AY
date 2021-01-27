package com.flam.flyay.util;

public enum FieldTypeEnum {
    STRING("STRING"),
    CATEGORY("CATEGORY"),
    SUBCATEGORY("SUBCATEGORY");

    public String name;

    FieldTypeEnum(String name) {
        this.name = name;
    }
}
