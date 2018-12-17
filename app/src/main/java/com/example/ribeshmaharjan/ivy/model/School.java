package com.example.ribeshmaharjan.ivy.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class School {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("images")
    @Expose
    private List<String> images = null;
    @SerializedName("schoolaverage")
    @Expose
    private Float schoolaverage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Float getSchoolaverage() {
        return schoolaverage;
    }

    public void setSchoolaverage(Float schoolaverage) {
        this.schoolaverage = schoolaverage;
    }

}