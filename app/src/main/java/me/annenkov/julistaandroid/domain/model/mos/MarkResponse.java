package me.annenkov.julistaandroid.domain.model.mos;

import java.util.List;

public class MarkResponse {
    private int scheduleLessonId;
    private String date;
    private String subject;
    private List<Integer> marks;

    public MarkResponse(int scheduleLessonId, String date, String subject, List<Integer> marks) {
        this.scheduleLessonId = scheduleLessonId;
        this.date = date;
        this.subject = subject;
        this.marks = marks;
    }

    public int getScheduleLessonId() {
        return scheduleLessonId;
    }

    public String getDate() {
        return date;
    }

    public String getSubject() {
        return subject;
    }

    public List<Integer> getMarks() {
        return marks;
    }
}
