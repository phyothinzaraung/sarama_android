package com.koekoetech.sayarma.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.koekoetech.sayarma.helper.Pageable;

import java.io.Serializable;

import io.realm.RealmObject;

public class MemberModel extends RealmObject implements Pageable, Serializable {

    @Expose
    @SerializedName("UserID")
    private String UserID;

    @Expose
    @SerializedName("OrganizationalID")
    private String OrganizationalID;

    @Expose
    @SerializedName("OrganizationDescription")
    private String OrganizationDescription;

    @Expose
    @SerializedName("Username")
    private String Username;

    @Expose
    @SerializedName("password")
    private String password;

    @Expose
    @SerializedName("nameinEnglish")
    private String nameinEnglish;

    @Expose
    @SerializedName("phoneNumber")
    private String phoneNumber;

    @Expose
    @SerializedName("Remark")
    private String Remark;

    @Expose
    @SerializedName("Gender")
    private String Gender;

    @Expose
    @SerializedName("DOB")
    private String  DOB;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getOrganizationalID() {
        return OrganizationalID;
    }

    public void setOrganizationalID(String organizationalID) {
        OrganizationalID = organizationalID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNameinEnglish() {
        return nameinEnglish;
    }

    public void setNameinEnglish(String nameinEnglish) {
        this.nameinEnglish = nameinEnglish;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOrganizationDescription() {
        return OrganizationDescription;
    }

    public void setOrganizationDescription(String organizationDescription) {
        OrganizationDescription = organizationDescription;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }
}
