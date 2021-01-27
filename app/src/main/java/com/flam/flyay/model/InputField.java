package com.flam.flyay.model;

import androidx.annotation.Nullable;

public class InputField {
    private int id;
    private String name;

    @Nullable
    private String labelName;

    private String fieldType;
    private int fieldOrderId;

    @Nullable
    private Integer fieldParentId;

    @Nullable
    private Double min;

    @Nullable
    private Double max;

    private boolean mandatory;


    public InputField(int id, String name,@Nullable String labelName, String fieldType, int fieldOrderId,@Nullable Integer fieldParentId,@Nullable Double min,@Nullable Double max, boolean mandatory) {
        this.id = id;
        this.name = name;
        this.labelName = labelName;
        this.fieldType = fieldType;
        this.fieldOrderId = fieldOrderId;
        this.fieldParentId = fieldParentId;
        this.min = min;
        this.max = max;
        this.mandatory = mandatory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(@Nullable String labelName) {
        this.labelName = labelName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public int getFieldOrderId() {
        return fieldOrderId;
    }

    public void setFieldOrderId(int fieldOrderId) {
        this.fieldOrderId = fieldOrderId;
    }

    public Integer getFieldParentId() {
        return fieldParentId;
    }

    public void setFieldParentId(Integer fieldParentId) {
        this.fieldParentId = fieldParentId;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }
}
