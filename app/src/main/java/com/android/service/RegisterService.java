package com.android.service;

import com.android.model.request.LoginRequest;
import com.android.model.request.RegisterRequest;
import com.android.payload.Resource;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegisterService {
    @POST("auth/register")
    Call<Resource<String>> registerNewAccount(@Body RegisterRequest request);

}
