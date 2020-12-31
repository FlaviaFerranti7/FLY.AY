package com.flam.flyay.model.subevent;

import com.flam.flyay.model.Event;
import com.flam.flyay.util.CategoryEnum;

import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.Map;

public class FestivityEvent extends Event {
    @Nullable
    private Double startingTime;

    public FestivityEvent(int id, String subcategory, String title, Date date, String note, @Nullable Double startingTime) {
        super(id, CategoryEnum.FESTIVITY.name, subcategory, title, date, note);
        this.startingTime = startingTime;
    }

    @Nullable
    public Double getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(@Nullable Double startingTime) {
        this.startingTime = startingTime;
    }

    @Override
    public Map<String, Object> getValueEvent() {
        Map<String, Object> valueEvent = super.getValueEvent();

        valueEvent.put("startingTime", this.startingTime);

        return valueEvent;
    }

    public String toString() {
        return super.toString() + " starting time: " + startingTime;
    }
}
