package com.android.service;

import com.android.payload.Resource;
import com.android.model.Province;
import com.android.model.request.PaymentOneWayRequest;
import com.android.model.request.PaymentTwoWayRequest;
import com.android.model.response.JourneyResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ReservationApiService {
    @GET("chuyenxe/get-chuyen-xe")
    Call<Resource<List<JourneyResponse>>> findJourney(@Query("maTinhDi") int origin,
                                                      @Query("maTinhDen") int destination,
                                                      @Query("ngayXuatPhat") String startDate);

    @GET("datve/get-all-province")
    Call<Resource<List<Province>>> getAllProvince();

    @POST("thanhtoan/zalopay-payment/oneway-trip")
    Call<String> createOrderOneWayTrip(@Body PaymentOneWayRequest request);

    @POST("thanhtoan/zalopay-payment/round-trip")
    Call<String> createOrderRoundTrip(@Body PaymentTwoWayRequest request);

}
