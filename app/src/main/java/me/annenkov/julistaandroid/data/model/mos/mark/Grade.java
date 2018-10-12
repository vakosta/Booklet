package me.annenkov.julistaandroid.data.model.mos.mark;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Grade {
    @SerializedName("origin")
    @Expose
    private String origin;

    @SerializedName("five")
    @Expose
    private Double five;

    @SerializedName("hundred")
    @Expose
    private Double hundred;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Double getFive() {
        return five;
    }

    public void setFive(Double five) {
        this.five = five;
    }

    public Double getHundred() {
        return hundred;
    }

    public void setHundred(Double hundred) {
        this.hundred = hundred;
    }
}
