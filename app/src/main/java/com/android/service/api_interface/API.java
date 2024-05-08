package com.android.service.api_interface;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public interface API {
    Retrofit builder = new Retrofit.Builder()
            .baseUrl("http://192.168.1.17:8080/")
            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
            .build();
}
