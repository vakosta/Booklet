package me.annenkov.julistaandroid.data.model.mos.schedule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeworkToVerify {
    @SerializedName("id")
    @Expose
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HomeworkToVerify that = (HomeworkToVerify) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
