package me.annenkov.julistaandroid.domain.model.mos;

import java.util.List;

public class ProgressResponse {
    private String subjectName;
    private float avgFive;
    private float avgHundred;
    private List<PeriodResponse> periods;

    public ProgressResponse(String subjectName, float avgFive, float avgHundred, List<PeriodResponse> periods) {
        this.subjectName = subjectName;
        this.avgFive = avgFive;
        this.avgHundred = avgHundred;
        this.periods = periods;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public float getAvgFive() {
        return avgFive;
    }

    public float getAvgHundred() {
        return avgHundred;
    }

    public List<PeriodResponse> getPeriods() {
        return periods;
    }
}
