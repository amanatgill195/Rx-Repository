package com.example.amanat.rcuproject.Retrofit;

import com.example.amanat.rcuproject.MyDeserializer;
import com.example.amanat.rcuproject.Worldpopulation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit ourInstance;

    static Gson type = new GsonBuilder()
            .registerTypeAdapter(Worldpopulation.class, new MyDeserializer<Worldpopulation>())
            .create();

    public static Retrofit getInstance() {
        if (ourInstance == null)
            ourInstance = new Retrofit.Builder()
                    .baseUrl("http://www.androidbegin.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        return ourInstance;
    }

    private RetrofitClient() {
    }
}
