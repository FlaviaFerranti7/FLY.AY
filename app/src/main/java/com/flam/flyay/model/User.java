package com.flam.flyay.model;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class User {
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String country;
    private String birthday;

    @Nullable
    private String password;
    private String email;



    public User() {}

    public User(int id, String username, String first_name, String last_name, String country, String birthday, @Nullable String password, String email) {
        this.id = id;
        this.username = username;
        this.firstName = first_name;
        this.lastName = last_name;
        this.country = country;
        this.birthday = birthday;
        this.password = password;
        this.email = email;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Nullable
    public String getPassword() {
        return password;
    }

    public void setPassword(@Nullable String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotNull
    public String toString() {
        return "User=> id: " + this.id + ";username: " + this.username + "; first_name: " + this.firstName + "; last_name: " + this.lastName
                + "; country: " + this.country + "; birthday: " + this.birthday + "; email: " + this.email + "; password: " + this.password +  ";";
    }
}
