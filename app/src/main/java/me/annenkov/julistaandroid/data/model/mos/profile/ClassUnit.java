package me.annenkov.julistaandroid.data.model.mos.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClassUnit {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("class_level_id")
    @Expose
    private Integer classLevelId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("home_based")
    @Expose
    private Boolean homeBased;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClassLevelId() {
        return classLevelId;
    }

    public void setClassLevelId(Integer classLevelId) {
        this.classLevelId = classLevelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getHomeBased() {
        return homeBased;
    }

    public void setHomeBased(Boolean homeBased) {
        this.homeBased = homeBased;
    }
}
