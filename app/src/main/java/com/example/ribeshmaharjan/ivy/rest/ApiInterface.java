package com.example.ribeshmaharjan.ivy.rest;


import com.example.ribeshmaharjan.ivy.RatingActivity;
import com.example.ribeshmaharjan.ivy.model.DetailResponse;
import com.example.ribeshmaharjan.ivy.model.FavouriteResponse;
import com.example.ribeshmaharjan.ivy.model.FeaturedResponse;
import com.example.ribeshmaharjan.ivy.model.FeeStructureResponse;
import com.example.ribeshmaharjan.ivy.model.LoginResponse;
import com.example.ribeshmaharjan.ivy.model.RatingResponse;
import com.example.ribeshmaharjan.ivy.model.ReviewResponse;
import com.example.ribeshmaharjan.ivy.model.SchoolResponse;
import com.example.ribeshmaharjan.ivy.model.cityresponse;


import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

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
    Call <SchoolResponse> getschools0()*/

    @GET("schooldetails/{id}")
    Call <DetailResponse> getschooldetail(@Path("id") int schoolID);

    @GET("review/{id}")
    Call<ReviewResponse> getallreviews(@Path("id")int schoolID);

    @POST("login")
    Call<LoginResponse> getlogindetail(@Query("email") String emailID, @Query("password") String password);

    @GET("featured")
    Call<FeaturedResponse> getfeatured();

    @POST("feerequest")
    Call<FeeStructureResponse> postfeerequest(
            @Query("schoolid") Integer schoolid,
            @Query("userid") Integer userid,
            @Query("name") String name,
            @Query("email") String email,
            @Query("phonenumber") String phonenumber,
            @Query("address") String address,
            @Query("childage") String childage);

    @POST("search")
    Call<SchoolResponse> getsearchresult(@Query("search") String search);

    @POST("appointment")
    Call<Void> postappointment(
            @Query("schoolid") Integer schoolid,
            @Query("userid") Integer userid,
            @Query("name") String name,
            @Query("email") String email,
            @Query("phonenumber") String phonenumber,
            @Query("address") String address,
            @Query("childage") String childage,
            @Query("date") String date);

    @POST("review")
    Call<RatingResponse> postreview(
            @Query("schoolid") Integer schoolid,
            @Query("userid") Integer userid,
            @Query("security") Float security,
            @Query("qualifiedstaff") Float qualifiedstaff,
            @Query("infrastructure") Float infrastructure,
            @Query("curriculum") Float curriculum,
            @Query("message") String message);

    @GET("favourite/{id}")
    Call<FavouriteResponse> getfavourite (@Path("id") int userID);
}

