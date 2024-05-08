package com.android.service.api_interface;

import com.android.service.APIResponse;
import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIProvince extends API {
    APIProvince service = builder.create(APIProvince.class);

    @GET("datve/get-all-province")
    Call<APIResponse<JsonArray>> listProvince();
}
