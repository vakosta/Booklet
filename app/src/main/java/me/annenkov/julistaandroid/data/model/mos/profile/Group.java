package me.annenkov.julistaandroid.data.model.mos.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Group {
    @SerializedName("user_profile_id")
    @Expose
    private Integer userProfileId;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("begin_date")
    @Expose
    private String beginDate;

    @SerializedName("end_date")
    @Expose
    private String endDate;

    @SerializedName("subgroup_ids")
    @Expose
    private Object subgroupIds;

    @SerializedName("class_unit_ids")
    @Expose
    private List<Integer> classUnitIds = null;

    @SerializedName("metagroup")
    @Expose
    private Boolean metagroup;

    @SerializedName("archived")
    @Expose
    private Boolean archived;

    public Integer getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(Integer userProfileId) {
        this.userProfileId = userProfileId;
    }

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

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Object getSubgroupIds() {
        return subgroupIds;
    }

    public void setSubgroupIds(Object subgroupIds) {
        this.subgroupIds = subgroupIds;
    }

    public List<Integer> getClassUnitIds() {
        return classUnitIds;
    }

    public void setClassUnitIds(List<Integer> classUnitIds) {
        this.classUnitIds = classUnitIds;
    }

    public Boolean getMetagroup() {
        return metagroup;
    }

    public void setMetagroup(Boolean metagroup) {
        this.metagroup = metagroup;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }
}
