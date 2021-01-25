package com.flam.flyay.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class Event implements Serializable {
    private int id;

    private String category;

    private String subcategory;

    private String title;

    private Date date;

    private String note;

    public Event() {}

    public Event(int id, String category, String subcategory, String title, Date date, String note) {
        this.id = id;
        this.category = category;
        this.subcategory = subcategory;
        this.title = title;
        this.note = note;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return this.subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @NotNull
    public String toString() {
        return "Event => id: " + this.id + " category: " + this.category
                + " subcategory: " + this.subcategory + " date"
                + this.date + " title: " + this.title + "note: " + this.note;
    }

    public Map<String, Object> getValueEvent() {
        Map<String, Object> valueEvent = new HashMap<>();

        valueEvent.put("id", this.id);
        valueEvent.put("category", this.category);
        valueEvent.put("subcategory", this.subcategory);
        valueEvent.put("title", this.title);
        valueEvent.put("date", this.date);
        valueEvent.put("note", this.note);

        return valueEvent;
    }

    public List<String> getKeySetSorted() {
        List<String> keySetSorted = new ArrayList<>();
        keySetSorted.add("title");
        keySetSorted.add("category");
        keySetSorted.add("subcategory");
        keySetSorted.add("date");

        return keySetSorted;
    }

}
