package com.flam.flyay.model.subevent;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.flam.flyay.util.Utils;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudyPlan implements Serializable {
    @Nullable
    private Date endStudy;

    @Nullable
    private List<String> studyingDays;

    @Nullable
    private List<String> overRange;

    @Nullable
    private Double startingOverRangeTime;

    @Nullable
    private Double endOverRangeTime;

    @Nullable
    private Double minStudyHours;

    @Nullable
    private Double maxStudyHours;

    @Nullable
    private Double minBreakHours;

    @Nullable
    private String examDifficulty;

    @Nullable
    private Integer safeDays;

    public StudyPlan(@Nullable Date endStudy, @Nullable List<String> studyingDays, @Nullable List<String> overRange, @Nullable Double startingOverRangeTime, @Nullable Double endOverRangeTime, @Nullable Double minStudyHours, @Nullable Double maxStudyHours, @Nullable Double minBreakHours, @Nullable String examDifficulty, @Nullable Integer safeDays) {
        this.endStudy = endStudy;
        this.studyingDays = studyingDays;
        this.overRange = overRange;
        this.startingOverRangeTime = startingOverRangeTime;
        this.endOverRangeTime = endOverRangeTime;
        this.minStudyHours = minStudyHours;
        this.maxStudyHours = maxStudyHours;
        this.minBreakHours = minBreakHours;
        this.examDifficulty = examDifficulty;
        this.safeDays = safeDays;
    }

    @Nullable
    public Date getEndStudy() {
        return endStudy;
    }

    public void setEndStudy(@Nullable Date endStudy) {
        this.endStudy = endStudy;
    }

    @Nullable
    public List<String> getStudyingDays() {
        return studyingDays;
    }

    public void setStudyingDays(@Nullable List<String> studyingDays) {
        this.studyingDays = studyingDays;
    }

    @Nullable
    public List<String> getOverRange() {
        return overRange;
    }

    public void setOverRange(@Nullable List<String> overRange) {
        this.overRange = overRange;
    }

    @Nullable
    public Double getStartingOverRangeTime() {
        return startingOverRangeTime;
    }

    public void setStartingOverRangeTime(@Nullable Double startingOverRangeTime) {
        this.startingOverRangeTime = startingOverRangeTime;
    }

    @Nullable
    public Double getEndOverRangeTime() {
        return endOverRangeTime;
    }

    public void setEndOverRangeTime(@Nullable Double endOverRangeTime) {
        this.endOverRangeTime = endOverRangeTime;
    }

    @Nullable
    public Double getMinStudyHours() {
        return minStudyHours;
    }

    public void setMinStudyHours(@Nullable Double minStudyHours) {
        this.minStudyHours = minStudyHours;
    }

    @Nullable
    public Double getMinBreakHours() {
        return minBreakHours;
    }

    public void setMinBreakHours(@Nullable Double minBreakHours) {
        this.minBreakHours = minBreakHours;
    }

    @Nullable
    public Integer getSafeDays() {
        return safeDays;
    }

    public void setSafeDays(@Nullable Integer safeDays) {
        this.safeDays = safeDays;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Map<String, Object> getValueEvent() {
        Map<String, Object> valueEvent = new HashMap<>();

        valueEvent.put("endStudy", Utils.convertionFromDateToString(this.endStudy));
        valueEvent.put("studyingDays", this.studyingDays);
        valueEvent.put("overRange", this.overRange);
        valueEvent.put("startingOverRangeTime", this.startingOverRangeTime);
        valueEvent.put("endOverRangeTime", this.endOverRangeTime);
        valueEvent.put("minStudyHours", Utils.convertionFromDoubleToTime(this.minStudyHours,':'));
        valueEvent.put("maxStudyHours", Utils.convertionFromDoubleToTime(this.maxStudyHours,':'));
        valueEvent.put("minBreakHours", Utils.convertionFromDoubleToTime(this.minBreakHours,':'));
        valueEvent.put("examDifficulty", this.examDifficulty);
        valueEvent.put("safeDays", this.safeDays);

        if(this.startingOverRangeTime != null && this.endOverRangeTime != null)
            valueEvent.put("timeOverRange", Utils.getTimeToString(this.startingOverRangeTime, this.endOverRangeTime));
        else if(this.startingOverRangeTime != null || this.endOverRangeTime != null)
            valueEvent.put("timeOverRange", Utils.convertionFromDoubleToTime(this.startingOverRangeTime != null ? this.startingOverRangeTime : this.endOverRangeTime, ':'));

        return valueEvent;
    }

    public List<String> getKeySetSorted() {
        List<String> keySetSorted = new ArrayList<>();
        keySetSorted.add("endStudy");
        keySetSorted.add("studyingDays");
        keySetSorted.add("overRange");
        keySetSorted.add("timeOverRange");
        keySetSorted.add("examDifficulty");
        keySetSorted.add("minStudyHours");
        keySetSorted.add("maxStudyHours");
        keySetSorted.add("minBreakHours");
        keySetSorted.add("safeDays");

        return keySetSorted;
    }

    @Nullable
    public String getExamDifficulty() {
        return examDifficulty;
    }

    public void setExamDifficulty(@Nullable String examDifficulty) {
        this.examDifficulty = examDifficulty;
    }

    @Nullable
    public Double getMaxStudyHours() {
        return maxStudyHours;
    }

    public void setMaxStudyHours(@Nullable Double maxStudyHours) {
        this.maxStudyHours = maxStudyHours;
    }
}
