package com.flam.flyay.model;

import com.flam.flyay.util.CategoryEnum;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class EventFinances extends Event{
    private Date date;
    private Date deadLine;
    private double price;


    public EventFinances(int id, String title, String note, Date date, Date deadLine, double price) {
        super(id, CategoryEnum.FINANCES, title, note);
        this.date = date;
        this.deadLine = deadLine;
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @NotNull
    public String toString() {
        return this.startToString() + "date: " + this.date + ";deadline: " + this.deadLine +
                ";end time: " + this.price + ";" + this.endToString();
    }
}
