package com.creatudevelopers.ribeshmaharjan.ivy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FavouriteResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("response_time")
    @Expose
    private Double responseTime;
    @SerializedName("results")
    @Expose
    private List<FavouriteResults> results = null;
    @SerializedName("request")
    @Expose
    private List<Object> request = null;

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

    public List<FavouriteResults> getResults() {
        return results;
    }

    public void setResults(List<FavouriteResults> results) {
        this.results = results;
    }

    public List<Object> getRequest() {
        return request;
    }

    public void setRequest(List<Object> request) {
        this.request = request;
    }
}
