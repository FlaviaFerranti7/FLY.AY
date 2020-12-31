package com.flam.flyay.model.subevent;

import com.flam.flyay.model.Event;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.Map;

public class FinancesEvent extends Event {

    @Nullable
    private Double price;

    @Nullable
    private String place;

    public FinancesEvent(int id, String category, String subcategory, String title, Date date, String note, @Nullable Double price, @Nullable String place) {
        super(id, category, subcategory, title, date, note);
        this.price = price;
        this.place = place;
    }

    @Nullable
    public Double getPrice() {
        return price;
    }

    public void setPrice(@Nullable Double price) {
        this.price = price;
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

        valueEvent.put("price", this.price);
        valueEvent.put("place", this.place);

        return valueEvent;
    }

    @Override
    @NotNull
    public String toString() {
        return super.toString() + " price: " + price + " place: " + place;
    }
}
