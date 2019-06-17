package me.annenkov.julistaandroid.data.model.mos.progress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Mark {
    @SerializedName("values")
    @Expose
    private List<Value> values = null;
    @SerializedName("weight")
    @Expose
    private Integer weight;
    @SerializedName("is_exam")
    @Expose
    private Boolean isExam;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("control_form_id")
    @Expose
    private Integer controlFormId;
    @SerializedName("grade_system_type")
    @Expose
    private String gradeSystemType;

    public List<Value> getValues() {
        return values;
    }

    public void setValues(List<Value> values) {
        this.values = values;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Boolean getIsExam() {
        return isExam;
    }

    public void setIsExam(Boolean isExam) {
        this.isExam = isExam;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getControlFormId() {
        return controlFormId;
    }

    public void setControlFormId(Integer controlFormId) {
        this.controlFormId = controlFormId;
    }

    public String getGradeSystemType() {
        return gradeSystemType;
    }

    public void setGradeSystemType(String gradeSystemType) {
        this.gradeSystemType = gradeSystemType;
    }
}