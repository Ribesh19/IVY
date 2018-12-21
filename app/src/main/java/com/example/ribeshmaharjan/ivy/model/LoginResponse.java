package com.example.ribeshmaharjan.ivy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("response_time")
    @Expose
    private Double responseTime;

    @SerializedName("results")
    @Expose
    private LoginResults results;

    @SerializedName("request")
    @Expose
    private LoginRequest request;
    
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Double responseTime) {
        this.responseTime = responseTime;
    }

    public LoginResults getResults() {
        return results;
    }

    public void setResults(LoginResults results) {
        this.results = results;
    }

    public LoginRequest getRequest() {
        return request;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public void setRequest(LoginRequest request) {
        this.request = request;
    }
}
