package me.annenkov.julistaandroid.data.model.mos.progress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Progress {
    @SerializedName("subject_name")
    @Expose
    private String subjectName;
    @SerializedName("periods")
    @Expose
    private List<Period> periods = null;
    @SerializedName("avg_five")
    @Expose
    private String avgFive;
    @SerializedName("avg_hundred")
    @Expose
    private String avgHundred;

    public Progress(String subjectName, List<Period> periods, String avgFive, String avgHundred) {
        this.subjectName = subjectName;
        this.periods = periods;
        this.avgFive = avgFive;
        this.avgHundred = avgHundred;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public void setPeriods(List<Period> periods) {
        this.periods = periods;
    }

    public String getAvgFive() {
        return avgFive;
    }

    public void setAvgFive(String avgFive) {
        this.avgFive = avgFive;
    }

    public String getAvgHundred() {
        return avgHundred;
    }

    public void setAvgHundred(String avgHundred) {
        this.avgHundred = avgHundred;
    }
}