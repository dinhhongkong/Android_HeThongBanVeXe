package com.android.service.api_interface;

import com.android.service.Response;
import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiClientJourney  {


    @GET("chuyenxe/get-chuyen-xe")
    Call<Response<JsonArray>> listJourney(@Query("maTinhDi") int origin,
                                          @Query("maTinhDen") int destination,
                                          @Query("ngayXuatPhat") String startDate);
}
