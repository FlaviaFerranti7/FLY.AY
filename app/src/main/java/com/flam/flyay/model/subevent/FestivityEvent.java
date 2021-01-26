package com.flam.flyay.model.subevent;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.flam.flyay.model.Event;
import com.flam.flyay.util.CategoryEnum;
import com.flam.flyay.util.Utils;

import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.List;
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Map<String, Object> getValueEvent() {
        Map<String, Object> valueEvent = super.getValueEvent();

        valueEvent.put("startingTime", this.startingTime);

        if(this.startingTime != null)
            valueEvent.put("time", Utils.convertionFromDoubleToTime(this.startingTime, ':'));

        return valueEvent;
    }

    public String toString() {
        return super.toString() + " starting time: " + startingTime;
    }

    @Override
    public List<String> getKeySetSorted() {
        List<String> keySetSorted = super.getKeySetSorted();
        keySetSorted.add("time");

        keySetSorted.add("note");
        return keySetSorted;
    }
}
