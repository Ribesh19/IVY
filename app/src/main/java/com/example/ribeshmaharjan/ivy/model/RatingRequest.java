package com.example.ribeshmaharjan.ivy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RatingRequest {
    @SerializedName("schoolid")
    @Expose
    private String schoolid;
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("security")
    @Expose
    private String security;
    @SerializedName("qualifiedstaff")
    @Expose
    private String qualifiedstaff;
    @SerializedName("infrastructure")
    @Expose
    private String infrastructure;
    @SerializedName("curriculum")
    @Expose
    private String curriculum;
    @SerializedName("message")
    @Expose
    private String message;

    public String getSchoolid() {
        return schoolid;
    }

    public void setSchoolid(String schoolid) {
        this.schoolid = schoolid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getQualifiedstaff() {
        return qualifiedstaff;
    }

    public void setQualifiedstaff(String qualifiedstaff) {
        this.qualifiedstaff = qualifiedstaff;
    }

    public String getInfrastructure() {
        return infrastructure;
    }

    public void setInfrastructure(String infrastructure) {
        this.infrastructure = infrastructure;
    }

    public String getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(String curriculum) {
        this.curriculum = curriculum;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
