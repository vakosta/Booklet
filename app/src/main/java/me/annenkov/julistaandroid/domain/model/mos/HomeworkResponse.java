package me.annenkov.julistaandroid.domain.model.mos;

import java.util.List;

public class HomeworkResponse {
    private int id;
    private String date;
    private String subject;
    private String description;
    private List<String> attachments;

    public HomeworkResponse(int id, String date, String subject, String description, List<String> attachments) {
        this.id = id;
        this.date = date;
        this.subject = subject;
        this.description = description;
        this.attachments = attachments;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getAttachments() {
        return attachments;
    }
}
