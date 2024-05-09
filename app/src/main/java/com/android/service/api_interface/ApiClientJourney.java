package com.android.service.api_interface;

import com.android.service.APIResponse;
import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiClientJourney extends ApiClient {
    ApiClientJourney service = builder.create(ApiClientJourney.class);

    @GET("chuyenxe/get-chuyen-xe")
    Call<APIResponse<JsonArray>> listJourney(@Query("maTinhDi") int origin,
                                             @Query("maTinhDen") int destination,
                                             @Query("ngayXuatPhat") String startDate);
}
