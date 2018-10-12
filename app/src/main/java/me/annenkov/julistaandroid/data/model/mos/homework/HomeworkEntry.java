package me.annenkov.julistaandroid.data.model.mos.homework;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeworkEntry {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;

    @SerializedName("homework_id")
    @Expose
    private Integer homeworkId;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("duration")
    @Expose
    private Integer duration;

    @SerializedName("no_duration")
    @Expose
    private Boolean noDuration;

    @SerializedName("homework")
    @Expose
    private Homework homework;

    @SerializedName("attachments")
    @Expose
    private List<Attachment> attachments = null;

    @SerializedName("controllable_items")
    @Expose
    private List<Object> controllableItems = null;

    @SerializedName("homework_entry_comments")
    @Expose
    private List<HomeworkEntryComment> homeworkEntryComments = null;

    @SerializedName("student_ids")
    @Expose
    private List<Integer> studentIds = null;

    @SerializedName("attachment_ids")
    @Expose
    private List<Integer> attachmentIds = null;

    @SerializedName("controllable_item_ids")
    @Expose
    private Object controllableItemIds;

    @SerializedName("books")
    @Expose
    private String books;

    @SerializedName("tests")
    @Expose
    private String tests;

    @SerializedName("scripts")
    @Expose
    private String scripts;

    @SerializedName("update_comment")
    @Expose
    private Object updateComment;

    @SerializedName("is_long_term")
    @Expose
    private Boolean isLongTerm;

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

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(Integer homeworkId) {
        this.homeworkId = homeworkId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Boolean getNoDuration() {
        return noDuration;
    }

    public void setNoDuration(Boolean noDuration) {
        this.noDuration = noDuration;
    }

    public Homework getHomework() {
        return homework;
    }

    public void setHomework(Homework homework) {
        this.homework = homework;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public List<Object> getControllableItems() {
        return controllableItems;
    }

    public void setControllableItems(List<Object> controllableItems) {
        this.controllableItems = controllableItems;
    }

    public List<HomeworkEntryComment> getHomeworkEntryComments() {
        return homeworkEntryComments;
    }

    public void setHomeworkEntryComments(List<HomeworkEntryComment> homeworkEntryComments) {
        this.homeworkEntryComments = homeworkEntryComments;
    }

    public List<Integer> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(List<Integer> studentIds) {
        this.studentIds = studentIds;
    }

    public List<Integer> getAttachmentIds() {
        return attachmentIds;
    }

    public void setAttachmentIds(List<Integer> attachmentIds) {
        this.attachmentIds = attachmentIds;
    }

    public Object getControllableItemIds() {
        return controllableItemIds;
    }

    public void setControllableItemIds(Object controllableItemIds) {
        this.controllableItemIds = controllableItemIds;
    }

    public String getBooks() {
        return books;
    }

    public void setBooks(String books) {
        this.books = books;
    }

    public String getTests() {
        return tests;
    }

    public void setTests(String tests) {
        this.tests = tests;
    }

    public String getScripts() {
        return scripts;
    }

    public void setScripts(String scripts) {
        this.scripts = scripts;
    }

    public Object getUpdateComment() {
        return updateComment;
    }

    public void setUpdateComment(Object updateComment) {
        this.updateComment = updateComment;
    }

    public Boolean getIsLongTerm() {
        return isLongTerm;
    }

    public void setIsLongTerm(Boolean isLongTerm) {
        this.isLongTerm = isLongTerm;
    }
}
