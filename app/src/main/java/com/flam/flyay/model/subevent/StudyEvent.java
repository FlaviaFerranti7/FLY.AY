package com.flam.flyay.model.subevent;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.flam.flyay.model.Event;
import com.flam.flyay.model.TeacherInfo;
import com.flam.flyay.util.CategoryEnum;
import com.flam.flyay.util.Utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.List;
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
    private TeacherInfo teacherInfo;

    @Nullable
    private StudyPlan studyPlan;


    public StudyEvent(int id, String subcategory, String title, Date date, String note, @Nullable Double startingTime, @Nullable Double endTime, @Nullable String place, @Nullable String room, @Nullable String teacherName, @Nullable String teacherEmail, @Nullable String teacherReceiptDate, @Nullable String teacherReceiptRoom, @Nullable StudyPlan studyPlan) {
        super(id, CategoryEnum.STUDY.name, subcategory, title, date, note);
        this.startingTime = startingTime;
        this.endTime = endTime;
        this.place = place;
        this.room = room;
        this.teacherInfo = new TeacherInfo(teacherName, teacherEmail, teacherReceiptDate, teacherReceiptRoom);
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
    public StudyPlan getStudyPlan() {
        return studyPlan;
    }

    public void setStudyPlan(@Nullable StudyPlan studyPlan) {
        this.studyPlan = studyPlan;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Map<String, Object> getValueEvent() {
        Map<String, Object> valueEvent = super.getValueEvent();

        valueEvent.put("startingTime", this.startingTime);
        valueEvent.put("endTime", this.endTime);
        valueEvent.put("room", this.room);
        valueEvent.put("place", this.place);

        if(this.startingTime != null && this.endTime != null)
            valueEvent.put("time", Utils.getTimeToString(this.startingTime, this.endTime));
        else if(this.startingTime != null || this.endTime != null)
            valueEvent.put("time", Utils.convertionFromDoubleToTime(this.startingTime != null ? this.startingTime : this.endTime, ':'));


        assert this.studyPlan != null;
        valueEvent.putAll(this.studyPlan.getValueEvent());

        assert this.teacherInfo != null;
        valueEvent.putAll(this.teacherInfo.getValueEvent());

        return valueEvent;
    }

    @Override
    public List<String> getKeySetSorted() {
        List<String> keySetSorted = super.getKeySetSorted();
        keySetSorted.add("time");
        keySetSorted.add("place");
        keySetSorted.add("room");

        if(teacherInfo != null)
            keySetSorted.addAll(teacherInfo.getKeySetSorted());

        if(studyPlan == null)
            keySetSorted.add("note");
        else {
            keySetSorted.addAll(studyPlan.getKeySetSorted());
        }

        return keySetSorted;
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
