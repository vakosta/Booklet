package me.annenkov.julistaandroid.data.model.mos.homework;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeworkBase {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("created_at")
    @Expose
    private Object createdAt;

    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;

    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;

    @SerializedName("student_id")
    @Expose
    private Integer studentId;

    @SerializedName("homework_entry_id")
    @Expose
    private Integer homeworkEntryId;

    @SerializedName("student_name")
    @Expose
    private String studentName;

    @SerializedName("comment")
    @Expose
    private Object comment;

    @SerializedName("is_ready")
    @Expose
    private Boolean isReady;

    @SerializedName("attachments")
    @Expose
    private List<Object> attachments = null;

    @SerializedName("remote_attachments")
    @Expose
    private List<Object> remoteAttachments = null;

    @SerializedName("homework_entry")
    @Expose
    private HomeworkEntry homeworkEntry;

    @SerializedName("attachment_ids")
    @Expose
    private List<Object> attachmentIds = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getHomeworkEntryId() {
        return homeworkEntryId;
    }

    public void setHomeworkEntryId(Integer homeworkEntryId) {
        this.homeworkEntryId = homeworkEntryId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Object getComment() {
        return comment;
    }

    public void setComment(Object comment) {
        this.comment = comment;
    }

    public Boolean getIsReady() {
        return isReady;
    }

    public void setIsReady(Boolean isReady) {
        this.isReady = isReady;
    }

    public List<Object> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Object> attachments) {
        this.attachments = attachments;
    }

    public List<Object> getRemoteAttachments() {
        return remoteAttachments;
    }

    public void setRemoteAttachments(List<Object> remoteAttachments) {
        this.remoteAttachments = remoteAttachments;
    }

    public HomeworkEntry getHomeworkEntry() {
        return homeworkEntry;
    }

    public void setHomeworkEntry(HomeworkEntry homeworkEntry) {
        this.homeworkEntry = homeworkEntry;
    }

    public List<Object> getAttachmentIds() {
        return attachmentIds;
    }

    public void setAttachmentIds(List<Object> attachmentIds) {
        this.attachmentIds = attachmentIds;
    }
}
