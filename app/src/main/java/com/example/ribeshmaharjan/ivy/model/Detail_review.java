package com.example.ribeshmaharjan.ivy.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Detail_review {
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("useraveragereview")
    @Expose
    private Float useraveragereview;
    @SerializedName("message")
    @Expose
    private String message;

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

}
