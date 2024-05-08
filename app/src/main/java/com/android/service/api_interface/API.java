package com.android.service.api_interface;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public interface API {
    String IP_ADDRESS = "192.168.1.17", PORT = "8080";
    Retrofit builder = new Retrofit.Builder()
            .baseUrl(String.format("http://%s:%s/", IP_ADDRESS, PORT))
            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
            .build();
}
