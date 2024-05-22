package com.android.service;

import com.android.core.Response;
import com.android.model.Province;
import com.android.model.response.JourneyResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ReservationApiService {
    @GET("chuyenxe/get-chuyen-xe")
    Call<Response<List<JourneyResponse>>> findJourney(@Query("maTinhDi") int origin,
                                                      @Query("maTinhDen") int destination,
                                                      @Query("ngayXuatPhat") String startDate);

    @GET("datve/get-all-province")
    Call<Response<List<Province>>> getAllProvince();
}