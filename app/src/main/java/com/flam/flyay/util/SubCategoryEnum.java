package com.flam.flyay.util;

import static com.flam.flyay.util.CategoryEnum.FESTIVITY;
import static com.flam.flyay.util.CategoryEnum.FINANCES;
import static com.flam.flyay.util.CategoryEnum.FREE_TIME;
import static com.flam.flyay.util.CategoryEnum.STUDY;
import static com.flam.flyay.util.CategoryEnum.WELLNESS;

public enum SubCategoryEnum {
    BODY_CARE(WELLNESS.name, "BODY_CARE"),
    MEDICINES(WELLNESS.name, "MEDICINES"),
    MED_APPOINTMENT(WELLNESS.name, "MED_APPOINTMENT"),

    REVENUE(FINANCES.name, "REVENUE"),
    OUTFLOW(FINANCES.name, "OUTFLOW"),

    FRIENDS(FREE_TIME.name, "FRIENDS"),
    FAMILY(FREE_TIME.name, "FAMILY"),
    HOBBY(FREE_TIME.name, "HOBBY"),
    TRAVELS(FREE_TIME.name, "TRAVELS"),
    BOOKS(FREE_TIME.name, "BOOKS"),
    FILMS_TV_SERIES(FREE_TIME.name, "FILMS_TV_SERIES"),
    THEATRE(FREE_TIME.name, "THEATRE"),
    MUSIC(FREE_TIME.name, "MUSIC"),
    SPORTIVE_EVENTS(FREE_TIME.name, "SPORTIVE_EVENTS"),
    SPORT(FREE_TIME.name, "SPORT"),
    OTHER(FREE_TIME.name, "OTHER"),


    EXAM(STUDY.name, "EXAM"),
    STUDY_TIME(STUDY.name, "STUDY_TIME"),
    LESSONS(STUDY.name, "LESSONS"),
    TEACHERS_OFFICE_HOURS(STUDY.name, "TEACHERS_OFFICE_HOURS"),
    STUDY_GROUP(STUDY.name, "STUDY_GROUP"),
    INTERSHIP(STUDY.name, "INTERSHIP"),

    BIRTHDAY(FESTIVITY.name, "BIRTHDAY"),
    LONG_WEEKEND(FESTIVITY.name, "LONG_WEEKEND"),
    HOLIDAYS(FESTIVITY.name, "HOLIDAYS");

    public String category;
    public String name;

    SubCategoryEnum(String category, String name) {
        this.category = category;
        this.name = name;
    }

}
