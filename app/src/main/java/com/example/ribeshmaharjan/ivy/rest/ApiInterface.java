package com.example.ribeshmaharjan.ivy.network;

import com.example.ribeshmaharjan.ivy.cityresponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public  interface ApiInterface {


    @GET("getcity")
    Call <cityresponse> getcity();



}
