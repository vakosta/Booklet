package me.annenkov.julistaandroid.data.model.mos.progress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Period {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("marks")
    @Expose
    private List<Mark> marks = null;
    @SerializedName("start")
    @Expose
    private String start;
    @SerializedName("end")
    @Expose
    private String end;
    @SerializedName("avg_five")
    @Expose
    private String avgFive;
    @SerializedName("avg_hundred")
    @Expose
    private String avgHundred;
    @SerializedName("final_mark")
    @Expose
    private String finalMark;

    public Period(String name, List<Mark> marks, String start, String end, String avgFive, String avgHundred, String finalMark) {
        this.name = name;
        this.marks = marks;
        this.start = start;
        this.end = end;
        this.avgFive = avgFive;
        this.avgHundred = avgHundred;
        this.finalMark = finalMark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public void setMarks(List<Mark> marks) {
        this.marks = marks;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
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

    public String getFinalMark() {
        return finalMark;
    }

    public void setFinalMark(String finalMark) {
        this.finalMark = finalMark;
    }
}