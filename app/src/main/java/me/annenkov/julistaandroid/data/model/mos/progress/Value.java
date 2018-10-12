package me.annenkov.julistaandroid.data.model.mos.progress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Value {
    @SerializedName("five")
    @Expose
    private Double five;
    @SerializedName("hundred")
    @Expose
    private Double hundred;
    @SerializedName("original")
    @Expose
    private String original;

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

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }
}