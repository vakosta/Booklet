package me.annenkov.julistaandroid.data.model.mos.mark;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Value {
    @SerializedName("grade_system_id")
    @Expose
    private Integer gradeSystemId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("nmax")
    @Expose
    private Double nmax;

    @SerializedName("grade_system_type")
    @Expose
    private String gradeSystemType;

    @SerializedName("grade")
    @Expose
    private Grade grade;

    public Integer getGradeSystemId() {
        return gradeSystemId;
    }

    public void setGradeSystemId(Integer gradeSystemId) {
        this.gradeSystemId = gradeSystemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getNmax() {
        return nmax;
    }

    public void setNmax(Double nmax) {
        this.nmax = nmax;
    }

    public String getGradeSystemType() {
        return gradeSystemType;
    }

    public void setGradeSystemType(String gradeSystemType) {
        this.gradeSystemType = gradeSystemType;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}
