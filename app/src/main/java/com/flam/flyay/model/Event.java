package com.flam.flyay.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

public class Event {
    private int id;

    private String category;

    private String title;

    @Nullable
    private Date date;

    @Nullable
    private Double startingTime;

    @Nullable
    private Double endTime;

    @Nullable
    private String place;

    @Nullable
    private Date deadLine;

    @Nullable
    private Double price;

    private String note;

    public Event() {}

    public Event(int id, String category, String title, String note, @Nullable Date date, @Nullable Double startingTime, @Nullable Double endTime, @Nullable String place, @Nullable Date deadLine, @Nullable Double price) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.note = note;
        this.date = date;
        this.startingTime = startingTime;
        this.endTime = endTime;
        this.place = place;
        this.deadLine = deadLine;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Double getEndTime() {
        return endTime;
    }

    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }

    public Double getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(double startingTime) {
        this.startingTime = startingTime;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @NotNull
    public String startToString() {
        return "Event => id: " + this.id + ";name: " + this.title + ";";
    }

    @NotNull
    public String endToString() {
        return "note: " + this.note;
    }

    @NotNull
    public String toString() {
        return this.startToString() + "date: " + this.date + ";starting time: " + this.startingTime +
                ";end time: " + this.endTime + ";place: " + this.place + ";" + this.endToString();
    }
}
