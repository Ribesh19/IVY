package com.creatudevelopers.ribeshmaharjan.ivy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Review {
    @SerializedName("securityaverage")
    @Expose
    private Float securityaverage;
    @SerializedName("staffaverage")
    @Expose
    private Float staffaverage;
    @SerializedName("infrastructureaverage")
    @Expose
    private Float infrastructureaverage;
    @SerializedName("curriculumaverage")
    @Expose
    private Float curriculumaverage;
    @SerializedName("reviews")
    @Expose
    private List<ReviewCollection> reviews = null;

    public Float getSecurityaverage() {
        return securityaverage;
    }

    public void setSecurityaverage(Float securityaverage) {
        this.securityaverage = securityaverage;
    }

    public Float getStaffaverage() {
        return staffaverage;
    }

    public void setStaffaverage(Float staffaverage) {
        this.staffaverage = staffaverage;
    }

    public Float getInfrastructureaverage() {
        return infrastructureaverage;
    }

    public void setInfrastructureaverage(Float infrastructureaverage) {
        this.infrastructureaverage = infrastructureaverage;
    }

    public Float getCurriculumaverage() {
        return curriculumaverage;
    }

    public void setCurriculumaverage(Float curriculumaverage) {
        this.curriculumaverage = curriculumaverage;
    }

    public List<ReviewCollection> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewCollection> reviews) {
        this.reviews = reviews;
    }

}
