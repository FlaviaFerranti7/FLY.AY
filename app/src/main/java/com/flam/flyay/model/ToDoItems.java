package com.flam.flyay.model;

import org.jetbrains.annotations.NotNull;

public class ToDoItems {
    private int id;
    private String title;
    private boolean checked;

    public ToDoItems() {}

    public ToDoItems(int id, String title, boolean checked) {
        this.id = id;
        this.title = title;
        this.checked = checked;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @NotNull
    public String toString() {
        return "ListItems => id: " + this.id + "; title: " + this.title  + "; checked: " + this.checked;
    }
}
