package com.flam.flyay.model;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

public class User {
    private int id;
    private String username;
    private String firstName;
    private String lastName;

    @Nullable
    private String password;
    private String email;


    public User() {}

    public User(int id, String username, String first_name, String last_name) {
        this.id = id;
        this.username = username;
        this.firstName = first_name;
        this.lastName = last_name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirst_name() {
        return firstName;
    }

    public void setFirst_name(String first_name) {
        this.firstName = first_name;
    }

    public String getLast_name() {
        return lastName;
    }

    public void setLast_name(String last_name) {
        this.lastName = last_name;
    }

    @NotNull
    public String startToString() {
        return "User=> id: " + this.id + ";username: " + this.username + "; first_name: " + this.firstName + "; last_name: " + this.lastName +  ";";
    }

}
