package me.annenkov.julistaandroid.data.model.mos.schedule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeworkToGive {
    @SerializedName("id")
    @Expose
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
