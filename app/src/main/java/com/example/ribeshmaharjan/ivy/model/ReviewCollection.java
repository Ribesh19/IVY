package com.example.ribeshmaharjan.ivy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewCollection {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("useraveragereview")
    @Expose
    private Float useraveragereview;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("date")
    @Expose
    private String date;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Float getUseraveragereview() {
        return useraveragereview;
    }

    public void setUseraveragereview(Float useraveragereview) {
        this.useraveragereview = useraveragereview;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

