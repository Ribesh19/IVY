package com.example.ribeshmaharjan.ivy.rest;


import com.example.ribeshmaharjan.ivy.model.DetailResponse;
import com.example.ribeshmaharjan.ivy.model.ReviewResponse;
import com.example.ribeshmaharjan.ivy.model.SchoolResponse;
import com.example.ribeshmaharjan.ivy.model.cityresponse;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public  interface ApiInterface {


    @GET("getcity")
    Call <cityresponse> getcity();

    @POST("schools")
    Call<SchoolResponse> getschools(@Query("city") String cityname);

   /* @POST("schools?city=Kirtipur")
    Call <SchoolResponse> getschools1();

    @POST("schools?city=Lalitpur")
    Call <SchoolResponse> getschools2();

    @POST("schools?city=Kathmandu")
    Call <SchoolResponse> getschools0()*/;

    @GET("schooldetails/{id}")
    Call <DetailResponse> getschooldetail(@Path("id") int schoolID);

    @GET("review/{id}")
    Call<ReviewResponse> getallreviews(@Path("id")int schoolID);



}
