package me.annenkov.julistaandroid.data.model.mos.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Parent {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("user_id")
    @Expose
    private Integer userId;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("gusoev_login")
    @Expose
    private Object gusoevLogin;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("phone_number_ezd")
    @Expose
    private Object phoneNumberEzd;

    @SerializedName("email_ezd")
    @Expose
    private Object emailEzd;

    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("snils")
    @Expose
    private Object snils;

    @SerializedName("last_sign_in_at")
    @Expose
    private Object lastSignInAt;

    @SerializedName("hidden")
    @Expose
    private Boolean hidden;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getGusoevLogin() {
        return gusoevLogin;
    }

    public void setGusoevLogin(Object gusoevLogin) {
        this.gusoevLogin = gusoevLogin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getPhoneNumberEzd() {
        return phoneNumberEzd;
    }

    public void setPhoneNumberEzd(Object phoneNumberEzd) {
        this.phoneNumberEzd = phoneNumberEzd;
    }

    public Object getEmailEzd() {
        return emailEzd;
    }

    public void setEmailEzd(Object emailEzd) {
        this.emailEzd = emailEzd;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getSnils() {
        return snils;
    }

    public void setSnils(Object snils) {
        this.snils = snils;
    }

    public Object getLastSignInAt() {
        return lastSignInAt;
    }

    public void setLastSignInAt(Object lastSignInAt) {
        this.lastSignInAt = lastSignInAt;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }
}
