package com.creatudevelopers.ribeshmaharjan.ivy.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Detail_Favourite {

    @SerializedName("is_favorite")
    @Expose
    private Integer isFavorite;

    public Integer getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(Integer isFavorite) {
        this.isFavorite = isFavorite;
    }

}
