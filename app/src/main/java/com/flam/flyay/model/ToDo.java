package com.flam.flyay.model;

import org.jetbrains.annotations.NotNull;

public class ToDo {
    private int id;
    private String title;
    private String color;
    private double lifetimer;
    private boolean checked;

    public ToDo(int id, String title, String color, double lifetimer, boolean checked){
        this.id = id;
        this.title = title;
        this.color = color;
        this.lifetimer = lifetimer;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getLifetimer() {
        return lifetimer;
    }

    public void setLifetimer(double lifetimer) {
        this.lifetimer = lifetimer;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @NotNull
    public String toString() {
        return "List => id: " + this.id + "; title: " + this.title + "; color: " + this.color + "; lifetimer: " + this.lifetimer +
                "; checked: " + this.checked;
    }

}
