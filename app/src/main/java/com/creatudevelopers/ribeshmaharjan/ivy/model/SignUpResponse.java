package com.creatudevelopers.ribeshmaharjan.ivy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUpResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("response_time")
    @Expose
    private Double responseTime;
    @SerializedName("results")
    @Expose
    private SignUpResult results;
    @SerializedName("request")
    @Expose
    private SignUpResult request;

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

    public SignUpResult getResults() {
        return results;
    }

    public void setResults(SignUpResult results) {
        this.results = results;
    }

    public SignUpResult getRequest() {
        return request;
    }

    public void setRequest(SignUpResult request) {
        this.request = request;
    }

}
