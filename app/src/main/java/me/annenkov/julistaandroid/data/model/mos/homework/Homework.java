package me.annenkov.julistaandroid.data.model.mos.homework;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Homework {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;

    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;

    @SerializedName("deleted_by")
    @Expose
    private Object deletedBy;

    @SerializedName("teacher_id")
    @Expose
    private Integer teacherId;

    @SerializedName("subject_id")
    @Expose
    private Integer subjectId;

    @SerializedName("is_required")
    @Expose
    private Boolean isRequired;

    @SerializedName("mark_required")
    @Expose
    private Boolean markRequired;

    @SerializedName("group_id")
    @Expose
    private Integer groupId;

    @SerializedName("date_assigned_on")
    @Expose
    private String dateAssignedOn;

    @SerializedName("date_prepared_for")
    @Expose
    private String datePreparedFor;

    @SerializedName("subject")
    @Expose
    private Subject subject;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
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

    public Object getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(Object deletedBy) {
        this.deletedBy = deletedBy;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Boolean getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }

    public Boolean getMarkRequired() {
        return markRequired;
    }

    public void setMarkRequired(Boolean markRequired) {
        this.markRequired = markRequired;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getDateAssignedOn() {
        return dateAssignedOn;
    }

    public void setDateAssignedOn(String dateAssignedOn) {
        this.dateAssignedOn = dateAssignedOn;
    }

    public String getDatePreparedFor() {
        return datePreparedFor;
    }

    public void setDatePreparedFor(String datePreparedFor) {
        this.datePreparedFor = datePreparedFor;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
