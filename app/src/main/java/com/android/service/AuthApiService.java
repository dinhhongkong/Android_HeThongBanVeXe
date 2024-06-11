package com.android.service;

import com.android.model.request.LoginRequest;
import com.android.payload.Resource;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface AuthApiService {
    @POST("auth/login")
    Call<Resource<String>> login(@Body LoginRequest request);

    @GET("auth/validate/{token}")
    Call<String> validate(@Path("token") String token);
}
