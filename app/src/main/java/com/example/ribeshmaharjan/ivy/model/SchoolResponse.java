package com.example.ribeshmaharjan.ivy.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SchoolResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("response_time")
    @Expose
    private Double responseTime;
    @SerializedName("results")
    @Expose
    private List<School> results = null;
    @SerializedName("request")
    @Expose
    private School request;

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

    public List<School> getResults() {
        return results;
    }

    public void setResults(List<School> results) {
        this.results = results;
    }

    public School getRequest() {
        return request;
    }

    public void setRequest(School request) {
        this.request = request;
    }

}
