package me.annenkov.julistaandroid.data.model.mos.session;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Session {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("snils")
    @Expose
    private String snils;

    @SerializedName("profiles")
    @Expose
    private List<Profile> profiles = null;

    @SerializedName("passwordHashWithoutSalt")
    @Expose
    private String passwordHashWithoutSalt;

    @SerializedName("guid")
    @Expose
    private String guid;

    @SerializedName("first_name")
    @Expose
    private String firstName;

    @SerializedName("last_name")
    @Expose
    private String lastName;

    @SerializedName("middle_name")
    @Expose
    private String middleName;

    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;

    @SerializedName("authentication_token")
    @Expose
    private String authenticationToken;

    @SerializedName("password_change_required")
    @Expose
    private Boolean passwordChangeRequired;

    @SerializedName("sex")
    @Expose
    private String sex;

    @SerializedName("date_of_birth")
    @Expose
    private String dateOfBirth;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSnils() {
        return snils;
    }

    public void setSnils(String snils) {
        this.snils = snils;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    public String getPasswordHashWithoutSalt() {
        return passwordHashWithoutSalt;
    }

    public void setPasswordHashWithoutSalt(String passwordHashWithoutSalt) {
        this.passwordHashWithoutSalt = passwordHashWithoutSalt;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public void setAuthenticationToken(String authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

    public Boolean getPasswordChangeRequired() {
        return passwordChangeRequired;
    }

    public void setPasswordChangeRequired(Boolean passwordChangeRequired) {
        this.passwordChangeRequired = passwordChangeRequired;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
