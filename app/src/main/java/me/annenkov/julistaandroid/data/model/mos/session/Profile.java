package me.annenkov.julistaandroid.data.model.mos.session;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Profile {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("roles")
    @Expose
    private List<Object> roles = null;

    @SerializedName("user_id")
    @Expose
    private Integer userId;

    @SerializedName("agree_pers_data")
    @Expose
    private Boolean agreePersData;

    @SerializedName("school_id")
    @Expose
    private Integer schoolId;

    @SerializedName("school_shortname")
    @Expose
    private String schoolShortname;

    @SerializedName("subject_ids")
    @Expose
    private List<Object> subjectIds = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Object> getRoles() {
        return roles;
    }

    public void setRoles(List<Object> roles) {
        this.roles = roles;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getAgreePersData() {
        return agreePersData;
    }

    public void setAgreePersData(Boolean agreePersData) {
        this.agreePersData = agreePersData;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolShortname() {
        return schoolShortname;
    }

    public void setSchoolShortname(String schoolShortname) {
        this.schoolShortname = schoolShortname;
    }

    public List<Object> getSubjectIds() {
        return subjectIds;
    }

    public void setSubjectIds(List<Object> subjectIds) {
        this.subjectIds = subjectIds;
    }
}
