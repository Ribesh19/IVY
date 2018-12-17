package com.example.ribeshmaharjan.ivy;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class cityresponse {

    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("response_time")
    @Expose
    private Double responseTime;

    @SerializedName("results")
    @Expose
    private List<city> results = null;

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

    public List<city> getResults() {
        return results;
    }

    public void setResults(List<city> results) {
        this.results = results;
    }

    public List<Object> getRequest() {
        return request;
    }

    public void setRequest(List<Object> request) {
        this.request = request;
    }

}