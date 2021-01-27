package com.flam.flyay.model;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class ToDo implements Serializable {
    private int id;
    private String title;
    private String image;
    private double lifetimer;
    private boolean checked;
    private int image_pos;

    public ToDo() {}

    public ToDo(int id, String title, String image, double lifetimer, boolean checked){
        this.id = id;
        this.title = title;
        this.image = image;
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

    public String getImage() {
        return image;
    }

    public void setImagePos(int pos) { image_pos = pos; }

    public int getImagePos(){ return image_pos;  }

    @NotNull
    public String toString() {
        return "List => id: " + this.id + "; title: " + this.title + "; image: " + this.image + "; lifetimer: " + this.lifetimer +
                "; checked: " + this.checked;
    }
}
