package com.flam.flyay.model.subevent;

import com.flam.flyay.model.Event;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WellnessEvent extends Event {
    @Nullable
    private Double startingTime;

    @Nullable
    private Double endTime;

    @Nullable
    private String place;

    public WellnessEvent(int id, String category, String subcategory, String title, Date date, String note, @Nullable Double startingTime, @Nullable Double endTime, @Nullable String place) {
        super(id, category, subcategory, title, date, note);
        this.startingTime = startingTime;
        this.endTime = endTime;
        this.place = place;
    }

    @Nullable
    public Double getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(@Nullable Double startingTime) {
        this.startingTime = startingTime;
    }

    @Nullable
    public Double getEndTime() {
        return endTime;
    }

    public void setEndTime(@Nullable Double endTime) {
        this.endTime = endTime;
    }

    @Nullable
    public String getPlace() {
        return place;
    }

    public void setPlace(@Nullable String place) {
        this.place = place;
    }

    @Override
    public Map<String, Object> getValueEvent() {
        Map<String, Object> valueEvent = super.getValueEvent();

        valueEvent.put("startingTime", this.startingTime);
        valueEvent.put("endTime", this.endTime);
        valueEvent.put("place", this.place);

        return valueEvent;
    }

    @Override
    @NotNull
    public String toString() {
        return super.toString() + " starting time: " + startingTime + " end time: " + endTime + " place: " + place;
    }
}
