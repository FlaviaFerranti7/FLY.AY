package com.flam.flyay.model;

import org.jetbrains.annotations.NotNull;

public class ToDoItems {
    private int id;
    private String name;
    private String title;
    private boolean checked;

    public ToDoItems() {}

    public ToDoItems(int id, String name, String title, boolean checked) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.checked = checked;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "ListItems => id: " + this.id + "; name: " + this.name + "; title: " + this.title  + "; checked: " + this.checked;
    }
}
