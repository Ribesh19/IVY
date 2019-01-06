package com.creatudevelopers.ribeshmaharjan.ivy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewCollection {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("useraveragereview")
    @Expose
    private Integer useraveragereview;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUseraveragereview() {
        return useraveragereview;
    }

    public void setUseraveragereview(Integer useraveragereview) {
        this.useraveragereview = useraveragereview;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}

