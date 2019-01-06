package com.creatudevelopers.ribeshmaharjan.ivy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeeStructureResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("response_time")
    @Expose
    private Double responseTime;
    @SerializedName("results")
    @Expose
    private String results;
    @SerializedName("request")
    @Expose
    private FeeStructure request;

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

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public FeeStructure getRequest() {
        return request;
    }

    public void setRequest(FeeStructure request) {
        this.request = request;
    }
}
