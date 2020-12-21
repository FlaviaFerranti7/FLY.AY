package com.flam.flyay.model;

import com.flam.flyay.util.CategoryEnum;

import org.jetbrains.annotations.NotNull;

public class Event {
    private int id;
    private String category;
    private String title;
    private String note;

    public Event() {}

    public Event(int id, String category, String title, String note) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.note = note;
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

    @NotNull
    public String startToString() {
        return "Event => id: " + this.id + ";name: " + this.title + ";";
    }

    @NotNull
    public String endToString() {
        return "note: " + this.note;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
