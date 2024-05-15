package com.android.service.api_interface;

import com.android.service.Response;
import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiClientProvince  {

    @GET("datve/get-all-province")
    Call<Response<JsonArray>> listProvince();
}
