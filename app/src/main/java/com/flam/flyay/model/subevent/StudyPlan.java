package com.flam.flyay.model.subevent;

import com.flam.flyay.model.Event;
import com.flam.flyay.util.CategoryEnum;

import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudyPlan extends Event {
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


    public StudyPlan(int id, String title, Date date, String note, @Nullable Date endingStudy, @Nullable List<String> studyingDays, @Nullable String overRange, @Nullable Double startingOverRangeTime, @Nullable Double endingOverRangeTime, @Nullable Double minStudyHours, @Nullable Double minBreakHours, @Nullable Integer safeDays) {
        super(id, CategoryEnum.STUDY.name, "STUDY-PLANNER", title, date, note);
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

    @Override
    public Map<String, Object> getValueEvent() {
        Map<String, Object> valueEvent = new HashMap<>();

        valueEvent.put("endingStudy", this.endingStudy);
        valueEvent.put("studyingDays", this.studyingDays);
        valueEvent.put("overRange", this.overRange);
        valueEvent.put("startingOverRangeTime", this.startingOverRangeTime);
        valueEvent.put("endingOverRangeTime", this.endingOverRangeTime);
        valueEvent.put("minStudyHours", this.minStudyHours);
        valueEvent.put("minBreakHours", this.minBreakHours);
        valueEvent.put("safeDays", this.safeDays);

        return valueEvent;
    }
}
