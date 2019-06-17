package me.annenkov.julistaandroid.data.model.mos.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Profile {
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

    @SerializedName("transferred")
    @Expose
    private Boolean transferred;

    @SerializedName("school_id")
    @Expose
    private Integer schoolId;

    @SerializedName("user_id")
    @Expose
    private Integer userId;

    @SerializedName("user_name")
    @Expose
    private String userName;

    @SerializedName("short_name")
    @Expose
    private String shortName;

    @SerializedName("last_name")
    @Expose
    private Object lastName;

    @SerializedName("first_name")
    @Expose
    private Object firstName;

    @SerializedName("middle_name")
    @Expose
    private Object middleName;

    @SerializedName("change_password_required")
    @Expose
    private Boolean changePasswordRequired;

    @SerializedName("birth_date")
    @Expose
    private String birthDate;

    @SerializedName("left_on")
    @Expose
    private Object leftOn;

    @SerializedName("enlisted_on")
    @Expose
    private Object enlistedOn;

    @SerializedName("gusoev_login")
    @Expose
    private String gusoevLogin;

    @SerializedName("age")
    @Expose
    private Integer age;

    @SerializedName("sex")
    @Expose
    private String sex;

    @SerializedName("deleted")
    @Expose
    private Boolean deleted;

    @SerializedName("email")
    @Expose
    private Object email;

    @SerializedName("phone_number")
    @Expose
    private Object phoneNumber;

    @SerializedName("email_ezd")
    @Expose
    private Object emailEzd;

    @SerializedName("phone_number_ezd")
    @Expose
    private Object phoneNumberEzd;

    @SerializedName("class_unit")
    @Expose
    private ClassUnit classUnit;

    @SerializedName("previously_class_unit")
    @Expose
    private Object previouslyClassUnit;

    @SerializedName("curricula")
    @Expose
    private Curricula curricula;

    @SerializedName("non_attendance")
    @Expose
    private Integer nonAttendance;

    @SerializedName("mentors")
    @Expose
    private List<Mentor> mentors = null;

    @SerializedName("ispp_account")
    @Expose
    private Integer isppAccount;

    @SerializedName("previously_profile_id")
    @Expose
    private Object previouslyProfileId;

    @SerializedName("student_viewed")
    @Expose
    private Object studentViewed;

    @SerializedName("education_level")
    @Expose
    private Object educationLevel;

    @SerializedName("class_level")
    @Expose
    private Object classLevel;

    @SerializedName("snils")
    @Expose
    private Object snils;

    @SerializedName("last_sign_in_at")
    @Expose
    private String lastSignInAt;

    @SerializedName("groups")
    @Expose
    private List<Group> groups = null;

    @SerializedName("parents")
    @Expose
    private List<Parent> parents = null;

    @SerializedName("marks")
    @Expose
    private List<Object> marks = null;

    @SerializedName("final_marks")
    @Expose
    private List<Object> finalMarks = null;

    @SerializedName("attendances")
    @Expose
    private List<Object> attendances = null;

    @SerializedName("lesson_comments")
    @Expose
    private List<Object> lessonComments = null;

    @SerializedName("home_based_periods")
    @Expose
    private List<Object> homeBasedPeriods = null;

    @SerializedName("subjects")
    @Expose
    private List<Object> subjects = null;

    @SerializedName("ae_attendances")
    @Expose
    private List<Object> aeAttendances = null;

    @SerializedName("ec_attendances")
    @Expose
    private List<Object> ecAttendances = null;

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

    public Boolean getTransferred() {
        return transferred;
    }

    public void setTransferred(Boolean transferred) {
        this.transferred = transferred;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Object getLastName() {
        return lastName;
    }

    public void setLastName(Object lastName) {
        this.lastName = lastName;
    }

    public Object getFirstName() {
        return firstName;
    }

    public void setFirstName(Object firstName) {
        this.firstName = firstName;
    }

    public Object getMiddleName() {
        return middleName;
    }

    public void setMiddleName(Object middleName) {
        this.middleName = middleName;
    }

    public Boolean getChangePasswordRequired() {
        return changePasswordRequired;
    }

    public void setChangePasswordRequired(Boolean changePasswordRequired) {
        this.changePasswordRequired = changePasswordRequired;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Object getLeftOn() {
        return leftOn;
    }

    public void setLeftOn(Object leftOn) {
        this.leftOn = leftOn;
    }

    public Object getEnlistedOn() {
        return enlistedOn;
    }

    public void setEnlistedOn(Object enlistedOn) {
        this.enlistedOn = enlistedOn;
    }

    public String getGusoevLogin() {
        return gusoevLogin;
    }

    public void setGusoevLogin(String gusoevLogin) {
        this.gusoevLogin = gusoevLogin;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Object getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Object phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Object getEmailEzd() {
        return emailEzd;
    }

    public void setEmailEzd(Object emailEzd) {
        this.emailEzd = emailEzd;
    }

    public Object getPhoneNumberEzd() {
        return phoneNumberEzd;
    }

    public void setPhoneNumberEzd(Object phoneNumberEzd) {
        this.phoneNumberEzd = phoneNumberEzd;
    }

    public ClassUnit getClassUnit() {
        return classUnit;
    }

    public void setClassUnit(ClassUnit classUnit) {
        this.classUnit = classUnit;
    }

    public Object getPreviouslyClassUnit() {
        return previouslyClassUnit;
    }

    public void setPreviouslyClassUnit(Object previouslyClassUnit) {
        this.previouslyClassUnit = previouslyClassUnit;
    }

    public Curricula getCurricula() {
        return curricula;
    }

    public void setCurricula(Curricula curricula) {
        this.curricula = curricula;
    }

    public Integer getNonAttendance() {
        return nonAttendance;
    }

    public void setNonAttendance(Integer nonAttendance) {
        this.nonAttendance = nonAttendance;
    }

    public List<Mentor> getMentors() {
        return mentors;
    }

    public void setMentors(List<Mentor> mentors) {
        this.mentors = mentors;
    }

    public Integer getIsppAccount() {
        return isppAccount;
    }

    public void setIsppAccount(Integer isppAccount) {
        this.isppAccount = isppAccount;
    }

    public Object getPreviouslyProfileId() {
        return previouslyProfileId;
    }

    public void setPreviouslyProfileId(Object previouslyProfileId) {
        this.previouslyProfileId = previouslyProfileId;
    }

    public Object getStudentViewed() {
        return studentViewed;
    }

    public void setStudentViewed(Object studentViewed) {
        this.studentViewed = studentViewed;
    }

    public Object getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(Object educationLevel) {
        this.educationLevel = educationLevel;
    }

    public Object getClassLevel() {
        return classLevel;
    }

    public void setClassLevel(Object classLevel) {
        this.classLevel = classLevel;
    }

    public Object getSnils() {
        return snils;
    }

    public void setSnils(Object snils) {
        this.snils = snils;
    }

    public String getLastSignInAt() {
        return lastSignInAt;
    }

    public void setLastSignInAt(String lastSignInAt) {
        this.lastSignInAt = lastSignInAt;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<Parent> getParents() {
        return parents;
    }

    public void setParents(List<Parent> parents) {
        this.parents = parents;
    }

    public List<Object> getMarks() {
        return marks;
    }

    public void setMarks(List<Object> marks) {
        this.marks = marks;
    }

    public List<Object> getFinalMarks() {
        return finalMarks;
    }

    public void setFinalMarks(List<Object> finalMarks) {
        this.finalMarks = finalMarks;
    }

    public List<Object> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<Object> attendances) {
        this.attendances = attendances;
    }

    public List<Object> getLessonComments() {
        return lessonComments;
    }

    public void setLessonComments(List<Object> lessonComments) {
        this.lessonComments = lessonComments;
    }

    public List<Object> getHomeBasedPeriods() {
        return homeBasedPeriods;
    }

    public void setHomeBasedPeriods(List<Object> homeBasedPeriods) {
        this.homeBasedPeriods = homeBasedPeriods;
    }

    public List<Object> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Object> subjects) {
        this.subjects = subjects;
    }

    public List<Object> getAeAttendances() {
        return aeAttendances;
    }

    public void setAeAttendances(List<Object> aeAttendances) {
        this.aeAttendances = aeAttendances;
    }

    public List<Object> getEcAttendances() {
        return ecAttendances;
    }

    public void setEcAttendances(List<Object> ecAttendances) {
        this.ecAttendances = ecAttendances;
    }
}
