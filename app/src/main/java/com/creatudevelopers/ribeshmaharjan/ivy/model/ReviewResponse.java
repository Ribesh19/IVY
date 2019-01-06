package com.creatudevelopers.ribeshmaharjan.ivy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("response_time")
    @Expose
    private Float responseTime;
    @SerializedName("results")
    @Expose
    private Review results;
    @SerializedName("request")
    @Expose
    private List<Review> request;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Float getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Float responseTime) {
        this.responseTime = responseTime;
    }

    public Review getResults() {
        return results;
    }

    public void setResults(Review results) {
        this.results = results;
    }

    public List<Review> getRequest() {
        return request;
    }

    public void setRequest(List<Review> request) {
        this.request = request;
    }

}
