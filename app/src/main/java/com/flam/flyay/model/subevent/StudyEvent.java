package com.flam.flyay.model.subevent;

import com.flam.flyay.model.Event;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.Map;

public class StudyEvent extends Event {
    @Nullable
    private Double startingTime;

    @Nullable
    private Double endTime;

    @Nullable
    private String room;


    public StudyEvent(int id, String category, String subcategory, String title, Date date, String note, @Nullable Double startingTime, @Nullable Double endTime, @Nullable String room) {
        super(id, category, subcategory, title, date, note);
        this.startingTime = startingTime;
        this.endTime = endTime;
        this.room = room;
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
    public String getRoom() {
        return room;
    }

    public void setRoom(@Nullable String room) {
        this.room = room;
    }

    @Override
    public Map<String, Object> getValueEvent() {
        Map<String, Object> valueEvent = super.getValueEvent();

        valueEvent.put("startingTime", this.startingTime);
        valueEvent.put("endTime", this.endTime);
        valueEvent.put("room", this.room);

        return valueEvent;
    }

    @Override
    @NotNull
    public String toString() {
        return super.toString() + " starting time: " + startingTime + " end time: " + endTime + " room: " + room;
    }
}
