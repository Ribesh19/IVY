package com.example.ribeshmaharjan.ivy.model;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Detail {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("post_code")
    @Expose
    private String postCode;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("is_publish")
    @Expose
    private Integer isPublish;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("transport")
    @Expose
    private String transport;
    @SerializedName("age_group")
    @Expose
    private String ageGroup;
    @SerializedName("facilities")
    @Expose
    private Object facilities;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("openingtime")
    @Expose
    private String openingtime;
    @SerializedName("closingtime")
    @Expose
    private String closingtime;
    @SerializedName("featured")
    @Expose
    private Integer featured;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("last_modified_by")
    @Expose
    private Object lastModifiedBy;
    @SerializedName("deleted_at")
    @Expose
    private String deletedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("images")
    @Expose
    private List<String> images = null;
    @SerializedName("favorite")
    @Expose
    private List<Detail_Favourite> favorite = null;
    @SerializedName("schoolaverage")
    @Expose
    private Float schoolaverage;
    @SerializedName("securityaverage")
    @Expose
    private Float securityaverage;
    @SerializedName("staffaverage")
    @Expose
    private Float staffaverage;
    @SerializedName("infrastructureaverage")
    @Expose
    private Float infrastructureaverage;
    @SerializedName("curriculumaverage")
    @Expose
    private Float curriculumaverage;
    @SerializedName("latest_review")
    @Expose
    private Detail_review latestReview;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getIsPublish() {
        return isPublish;
    }

    public void setIsPublish(Integer isPublish) {
        this.isPublish = isPublish;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public Object getFacilities() {
        return facilities;
    }

    public void setFacilities(Object facilities) {
        this.facilities = facilities;
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

    public String getOpeningtime() {
        return openingtime;
    }

    public void setOpeningtime(String openingtime) {
        this.openingtime = openingtime;
    }

    public String getClosingtime() {
        return closingtime;
    }

    public void setClosingtime(String closingtime) {
        this.closingtime = closingtime;
    }

    public Integer getFeatured() {
        return featured;
    }

    public void setFeatured(Integer featured) {
        this.featured = featured;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Object getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(Object lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<Detail_Favourite> getFavorite() {
        return favorite;
    }

    public void setFavorite(List<Detail_Favourite> favorite) {
        this.favorite = favorite;
    }

    public Float getSchoolaverage() {
        return schoolaverage;
    }

    public void setSchoolaverage(Float schoolaverage) {
        this.schoolaverage = schoolaverage;
    }

    public Float getSecurityaverage() {
        return securityaverage;
    }

    public void setSecurityaverage(Float securityaverage) {
        this.securityaverage = securityaverage;
    }

    public Float getStaffaverage() {
        return staffaverage;
    }

    public void setStaffaverage(Float staffaverage) {
        this.staffaverage = staffaverage;
    }

    public Float getInfrastructureaverage() {
        return infrastructureaverage;
    }

    public void setInfrastructureaverage(Float infrastructureaverage) {
        this.infrastructureaverage = infrastructureaverage;
    }

    public Float getCurriculumaverage() {
        return curriculumaverage;
    }

    public void setCurriculumaverage(Float curriculumaverage) {
        this.curriculumaverage = curriculumaverage;
    }

    public Detail_review getLatestReview() {
        return latestReview;
    }

    public void setLatestReview(Detail_review latestReview) {
        this.latestReview = latestReview;
    }
}
