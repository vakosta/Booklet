package me.annenkov.julistaandroid.data.model.mos.group;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Group {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("short_name")
    @Expose
    private String shortName;

    @SerializedName("subject_id")
    @Expose
    private Integer subjectId;

    @SerializedName("subject_name")
    @Expose
    private String subjectName;

    @SerializedName("parent_subject_id")
    @Expose
    private Object parentSubjectId;

    @SerializedName("periods_schedule_id")
    @Expose
    private Integer periodsScheduleId;

    @SerializedName("class_level_id")
    @Expose
    private Integer classLevelId;

    @SerializedName("class_unit_id")
    @Expose
    private Integer classUnitId;

    @SerializedName("class_unit_ids")
    @Expose
    private List<Integer> classUnitIds = null;

    @SerializedName("class_unit_name")
    @Expose
    private String classUnitName;

    @SerializedName("marker")
    @Expose
    private Integer marker;

    @SerializedName("student_count")
    @Expose
    private Integer studentCount;

    @SerializedName("subgroup_ids")
    @Expose
    private Object subgroupIds;

    @SerializedName("student_ids")
    @Expose
    private List<Integer> studentIds = null;

    @SerializedName("incompatible_group_ids")
    @Expose
    private List<Object> incompatibleGroupIds = null;

    @SerializedName("academic_year_id")
    @Expose
    private Integer academicYearId;

    @SerializedName("teachers")
    @Expose
    private Object teachers;

    @SerializedName("attestation_periods_schedule_id")
    @Expose
    private Integer attestationPeriodsScheduleId;

    @SerializedName("parallel_curriculum_id")
    @Expose
    private List<Integer> parallelCurriculumId = null;

    @SerializedName("is_metagroup")
    @Expose
    private Boolean isMetagroup;

    @SerializedName("is_final_by_periods")
    @Expose
    private Boolean isFinalByPeriods;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Object getParentSubjectId() {
        return parentSubjectId;
    }

    public void setParentSubjectId(Object parentSubjectId) {
        this.parentSubjectId = parentSubjectId;
    }

    public Integer getPeriodsScheduleId() {
        return periodsScheduleId;
    }

    public void setPeriodsScheduleId(Integer periodsScheduleId) {
        this.periodsScheduleId = periodsScheduleId;
    }

    public Integer getClassLevelId() {
        return classLevelId;
    }

    public void setClassLevelId(Integer classLevelId) {
        this.classLevelId = classLevelId;
    }

    public Integer getClassUnitId() {
        return classUnitId;
    }

    public void setClassUnitId(Integer classUnitId) {
        this.classUnitId = classUnitId;
    }

    public List<Integer> getClassUnitIds() {
        return classUnitIds;
    }

    public void setClassUnitIds(List<Integer> classUnitIds) {
        this.classUnitIds = classUnitIds;
    }

    public String getClassUnitName() {
        return classUnitName;
    }

    public void setClassUnitName(String classUnitName) {
        this.classUnitName = classUnitName;
    }

    public Integer getMarker() {
        return marker;
    }

    public void setMarker(Integer marker) {
        this.marker = marker;
    }

    public Integer getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(Integer studentCount) {
        this.studentCount = studentCount;
    }

    public Object getSubgroupIds() {
        return subgroupIds;
    }

    public void setSubgroupIds(Object subgroupIds) {
        this.subgroupIds = subgroupIds;
    }

    public List<Integer> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(List<Integer> studentIds) {
        this.studentIds = studentIds;
    }

    public List<Object> getIncompatibleGroupIds() {
        return incompatibleGroupIds;
    }

    public void setIncompatibleGroupIds(List<Object> incompatibleGroupIds) {
        this.incompatibleGroupIds = incompatibleGroupIds;
    }

    public Integer getAcademicYearId() {
        return academicYearId;
    }

    public void setAcademicYearId(Integer academicYearId) {
        this.academicYearId = academicYearId;
    }

    public Object getTeachers() {
        return teachers;
    }

    public void setTeachers(Object teachers) {
        this.teachers = teachers;
    }

    public Integer getAttestationPeriodsScheduleId() {
        return attestationPeriodsScheduleId;
    }

    public void setAttestationPeriodsScheduleId(Integer attestationPeriodsScheduleId) {
        this.attestationPeriodsScheduleId = attestationPeriodsScheduleId;
    }

    public List<Integer> getParallelCurriculumId() {
        return parallelCurriculumId;
    }

    public void setParallelCurriculumId(List<Integer> parallelCurriculumId) {
        this.parallelCurriculumId = parallelCurriculumId;
    }

    public Boolean getIsMetagroup() {
        return isMetagroup;
    }

    public void setIsMetagroup(Boolean isMetagroup) {
        this.isMetagroup = isMetagroup;
    }

    public Boolean getIsFinalByPeriods() {
        return isFinalByPeriods;
    }

    public void setIsFinalByPeriods(Boolean isFinalByPeriods) {
        this.isFinalByPeriods = isFinalByPeriods;
    }
}
