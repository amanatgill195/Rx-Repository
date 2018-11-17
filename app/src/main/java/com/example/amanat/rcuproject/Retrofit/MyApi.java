package com.example.amanat.rcuproject.Retrofit;

import com.example.amanat.rcuproject.JSONResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyApi {
    @GET("tutorial/jsonparsetutorial.txt")
    Call<JSONResponse> getJSON();
}
