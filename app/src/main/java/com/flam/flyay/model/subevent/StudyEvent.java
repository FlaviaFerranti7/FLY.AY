package com.flam.flyay.model.subevent;

import com.flam.flyay.model.Event;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.Map;

public class StudyEvent extends Event {
    @Nullable
    private Double startingTime;

    @Nullable
    private Double endTime;

    @Nullable
    private String place;

    @Nullable
    private String room;

    @Nullable
    private String teacherName;

    @Nullable
    private String teacherEmail;

    @Nullable
    private String teacherReceiptDate;

    @Nullable
    private String teacherReceiptRoom;

    @Nullable
    private StudyPlan studyPlan;


    public StudyEvent(int id, String category, String subcategory, String title, Date date, String note, @Nullable Double startingTime, @Nullable Double endTime, @Nullable String place, @Nullable String room, @Nullable String teacherName, @Nullable String teacherEmail, @Nullable String teacherReceiptDate, @Nullable String teacherReceiptRoom, @Nullable StudyPlan studyPlan) {
        super(id, category, subcategory, title, date, note);
        this.startingTime = startingTime;
        this.endTime = endTime;
        this.place = place;
        this.room = room;
        this.teacherName = teacherName;
        this.teacherEmail = teacherEmail;
        this.teacherReceiptDate = teacherReceiptDate;
        this.teacherReceiptRoom = teacherReceiptRoom;
        this.studyPlan = studyPlan;
    }

    @Nullable
    public Double getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(@Nullable Double startingTime) {
        this.startingTime = startingTime;
    }

    @Nullable
    public Double getEndTime() {
        return endTime;
    }

    public void setEndTime(@Nullable Double endTime) {
        this.endTime = endTime;
    }

    @Nullable
    public String getRoom() {
        return room;
    }

    public void setRoom(@Nullable String room) {
        this.room = room;
    }

    @Nullable
    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(@Nullable String teacherName) {
        this.teacherName = teacherName;
    }

    @Nullable
    public String getTeacherEmail() {
        return teacherEmail;
    }

    public void setTeacherEmail(@Nullable String teacherEmail) {
        this.teacherEmail = teacherEmail;
    }

    @Nullable
    public String getTeacherReceiptDate() {
        return teacherReceiptDate;
    }

    public void setTeacherReceiptDate(@Nullable String teacherReceiptDate) {
        this.teacherReceiptDate = teacherReceiptDate;
    }

    @Nullable
    public String getTeacherReceiptRoom() {
        return teacherReceiptRoom;
    }

    public void setTeacherReceiptRoom(@Nullable String teacherReceiptRoom) {
        this.teacherReceiptRoom = teacherReceiptRoom;
    }

    @Nullable
    public StudyPlan getStudyPlan() {
        return studyPlan;
    }

    public void setStudyPlan(@Nullable StudyPlan studyPlan) {
        this.studyPlan = studyPlan;
    }

    @Override
    public Map<String, Object> getValueEvent() {
        Map<String, Object> valueEvent = super.getValueEvent();

        valueEvent.put("startingTime", this.startingTime);
        valueEvent.put("endTime", this.endTime);
        valueEvent.put("room", this.room);
        valueEvent.put("place", this.place);
        valueEvent.put("teacherName", this.teacherName);
        valueEvent.put("teacherEmail", this.teacherEmail);
        valueEvent.put("teacherReceiptDate", this.teacherReceiptDate);
        valueEvent.put("teacherReceiptRoom", this.teacherReceiptRoom);


        assert this.studyPlan != null;
        valueEvent.putAll(this.studyPlan.getValueEvent());

        return valueEvent;
    }

    @Override
    @NotNull
    public String toString() {
        return super.toString() + " starting time: " + startingTime + " end time: " + endTime + " room: " + room;
    }

    @Nullable
    public String getPlace() {
        return place;
    }

    public void setPlace(@Nullable String place) {
        this.place = place;
    }
}
