package me.annenkov.julistaandroid.data.model.mos.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Curricula {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("class_level_id")
    @Expose
    private Object classLevelId;

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

    public Object getClassLevelId() {
        return classLevelId;
    }

    public void setClassLevelId(Object classLevelId) {
        this.classLevelId = classLevelId;
    }
}
