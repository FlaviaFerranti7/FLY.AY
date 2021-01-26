package com.flam.flyay.model.subevent;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.flam.flyay.model.Event;
import com.flam.flyay.util.CategoryEnum;
import com.flam.flyay.util.Utils;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudyPlan {
    @Nullable
    private Date endingStudy;

    @Nullable
    private List<String> studyingDays;

    @Nullable
    private String overRange;

    @Nullable
    private Double startingOverRangeTime;

    @Nullable
    private Double endingOverRangeTime;

    @Nullable
    private Double minStudyHours;

    @Nullable
    private Double minBreakHours;

    @Nullable
    private Integer safeDays;

    public StudyPlan(@Nullable Date endingStudy, @Nullable List<String> studyingDays, @Nullable String overRange, @Nullable Double startingOverRangeTime, @Nullable Double endingOverRangeTime, @Nullable Double minStudyHours, @Nullable Double minBreakHours, @Nullable Integer safeDays) {
        this.endingStudy = endingStudy;
        this.studyingDays = studyingDays;
        this.overRange = overRange;
        this.startingOverRangeTime = startingOverRangeTime;
        this.endingOverRangeTime = endingOverRangeTime;
        this.minStudyHours = minStudyHours;
        this.minBreakHours = minBreakHours;
        this.safeDays = safeDays;
    }

    @Nullable
    public Date getEndingStudy() {
        return endingStudy;
    }

    public void setEndingStudy(@Nullable Date endingStudy) {
        this.endingStudy = endingStudy;
    }

    @Nullable
    public List<String> getStudyingDays() {
        return studyingDays;
    }

    public void setStudyingDays(@Nullable List<String> studyingDays) {
        this.studyingDays = studyingDays;
    }

    @Nullable
    public String getOverRange() {
        return overRange;
    }

    public void setOverRange(@Nullable String overRange) {
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
    public Double getEndingOverRangeTime() {
        return endingOverRangeTime;
    }

    public void setEndingOverRangeTime(@Nullable Double endingOverRangeTime) {
        this.endingOverRangeTime = endingOverRangeTime;
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

        valueEvent.put("endingStudy", Utils.convertionFromDateToString(this.endingStudy));
        valueEvent.put("studyingDays", this.studyingDays);
        valueEvent.put("overRange", this.overRange);
        valueEvent.put("startingOverRangeTime", this.startingOverRangeTime);
        valueEvent.put("endingOverRangeTime", this.endingOverRangeTime);
        valueEvent.put("minStudyHours", this.minStudyHours);
        valueEvent.put("minBreakHours", this.minBreakHours);
        valueEvent.put("safeDays", this.safeDays);

        if(this.startingOverRangeTime != null && this.endingOverRangeTime != null)
            valueEvent.put("timeOverRange", Utils.getTimeToString(this.startingOverRangeTime, this.endingOverRangeTime));
        else if(this.startingOverRangeTime != null || this.endingOverRangeTime != null)
            valueEvent.put("timeOverRange", Utils.convertionFromDoubleToTime(this.startingOverRangeTime != null ? this.startingOverRangeTime : this.endingOverRangeTime, ':'));

        return valueEvent;
    }

    public List<String> getKeySetSorted() {
        List<String> keySetSorted = new ArrayList<>();
        keySetSorted.add("endingStudy");
        keySetSorted.add("studyingDays");
        keySetSorted.add("overRange");
        keySetSorted.add("timeOverRange");
        keySetSorted.add("minStudyHours");
        keySetSorted.add("minBreakHours");
        keySetSorted.add("safeDays");

        return keySetSorted;
    }
}
