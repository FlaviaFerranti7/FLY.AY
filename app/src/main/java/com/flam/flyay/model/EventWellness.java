package com.flam.flyay.model;

import com.flam.flyay.util.CategoryEnum;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class EventWellness extends Event{
    private Date date;
    private double startingTime;
    private double endTime;
    private String place;

    public EventWellness(
            int id,
            String title,
            String note,
            Date date,
            double startingTime,
            double endTime,
            String place
    ) {
        super(id, CategoryEnum.WELLNESS.name,title, note);

        this.date = date;
        this.startingTime = startingTime;
        this.endTime = endTime;
        this.place = place;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public double getEndTime() {
        return endTime;
    }

    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }

    public double getStartingTime() {
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
    public String toString() {
        return this.startToString() + "date: " + this.date + ";starting time: " + this.startingTime +
                ";end time: " + this.endTime + ";place: " + this.place + ";" + this.endToString();
    }
}
