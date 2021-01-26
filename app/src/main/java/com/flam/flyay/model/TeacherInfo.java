package com.flam.flyay.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.flam.flyay.util.Utils;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherInfo {
    @Nullable
    private String name;

    @Nullable
    private String email;

    @Nullable
    private String receiptDate;

    @Nullable
    private String receiptRoom;

    public TeacherInfo(@Nullable String name, @Nullable String email, @Nullable String receiptDate, @Nullable String receiptRoom) {
        this.name = name;
        this.email = email;
        this.receiptDate = receiptDate;
        this.receiptRoom = receiptRoom;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    public void setEmail(@Nullable String email) {
        this.email = email;
    }

    @Nullable
    public String getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(@Nullable String receiptDate) {
        this.receiptDate = receiptDate;
    }

    @Nullable
    public String getReceiptRoom() {
        return receiptRoom;
    }

    public void setReceiptRoom(@Nullable String receiptRoom) {
        this.receiptRoom = receiptRoom;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Map<String, Object> getValueEvent() {
        Map<String, Object> valueEvent = new HashMap<>();

        valueEvent.put("teacher", this);

        return valueEvent;
    }

    public List<String> getKeySetSorted() {
        List<String> keySetSorted = new ArrayList<>();
        keySetSorted.add("teacher");

        return keySetSorted;
    }

    @Override
    public String toString() {
        return "TeacherInfo{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", receiptDate='" + receiptDate + '\'' +
                ", receiptRoom='" + receiptRoom + '\'' +
                '}';
    }
}
