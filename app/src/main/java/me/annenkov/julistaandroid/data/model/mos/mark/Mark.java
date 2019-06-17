package me.annenkov.julistaandroid.data.model.mos.mark;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Mark {
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("student_profile_id")
    @Expose
    private Integer studentProfileId;

    @SerializedName("weight")
    @Expose
    private Integer weight;

    @SerializedName("teacher_id")
    @Expose
    private Integer teacherId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("comment")
    @Expose
    private Object comment;

    @SerializedName("control_form_id")
    @Expose
    private Integer controlFormId;

    @SerializedName("deleted_by")
    @Expose
    private Object deletedBy;

    @SerializedName("grade_id")
    @Expose
    private Integer gradeId;

    @SerializedName("schedule_lesson_id")
    @Expose
    private Integer scheduleLessonId;

    @SerializedName("is_exam")
    @Expose
    private Boolean isExam;

    @SerializedName("group_id")
    @Expose
    private Integer groupId;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("is_point")
    @Expose
    private Boolean isPoint;

    @SerializedName("point_date")
    @Expose
    private Object pointDate;

    @SerializedName("subject_id")
    @Expose
    private Integer subjectId;

    @SerializedName("grade_system_id")
    @Expose
    private Integer gradeSystemId;

    @SerializedName("grade_system_type")
    @Expose
    private String gradeSystemType;

    @SerializedName("values")
    @Expose
    private List<Value> values = null;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentProfileId() {
        return studentProfileId;
    }

    public void setStudentProfileId(Integer studentProfileId) {
        this.studentProfileId = studentProfileId;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getComment() {
        return comment;
    }

    public void setComment(Object comment) {
        this.comment = comment;
    }

    public Integer getControlFormId() {
        return controlFormId;
    }

    public void setControlFormId(Integer controlFormId) {
        this.controlFormId = controlFormId;
    }

    public Object getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(Object deletedBy) {
        this.deletedBy = deletedBy;
    }

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public Integer getScheduleLessonId() {
        return scheduleLessonId;
    }

    public void setScheduleLessonId(Integer scheduleLessonId) {
        this.scheduleLessonId = scheduleLessonId;
    }

    public Boolean getIsExam() {
        return isExam;
    }

    public void setIsExam(Boolean isExam) {
        this.isExam = isExam;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getIsPoint() {
        return isPoint;
    }

    public void setIsPoint(Boolean isPoint) {
        this.isPoint = isPoint;
    }

    public Object getPointDate() {
        return pointDate;
    }

    public void setPointDate(Object pointDate) {
        this.pointDate = pointDate;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getGradeSystemId() {
        return gradeSystemId;
    }

    public void setGradeSystemId(Integer gradeSystemId) {
        this.gradeSystemId = gradeSystemId;
    }

    public String getGradeSystemType() {
        return gradeSystemType;
    }

    public void setGradeSystemType(String gradeSystemType) {
        this.gradeSystemType = gradeSystemType;
    }

    public List<Value> getValues() {
        return values;
    }

    public void setValues(List<Value> values) {
        this.values = values;
    }
}
