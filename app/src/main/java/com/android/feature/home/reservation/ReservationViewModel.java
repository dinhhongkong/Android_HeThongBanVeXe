package com.android.feature.home.reservation;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.model.Province;
import com.android.model.Ticket;
import com.android.model.request.JourneyRequest;
import com.android.model.request.PaymentOneWayRequest;
import com.android.model.request.PaymentTwoWayRequest;
import com.android.model.response.JourneyResponse;
import com.android.service.ReservationApiService;
import com.android.payload.Resource;
import com.android.service.api_interface.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ReservationViewModel extends ViewModel {

    private ReservationApiService reservationApiService;
    private SharedPreferences sharedPrefer;
    private String token;
    private MutableLiveData<Boolean> isRoundTrip = new MutableLiveData<>(false);
    private MutableLiveData<List<Province>> provinces = new MutableLiveData<>(null);
    private MutableLiveData<JourneyRequest> journey = new MutableLiveData<>(new JourneyRequest());
    private MutableLiveData<List<JourneyResponse>> departureJourneyList = new MutableLiveData<>(null);
    private MutableLiveData<List<JourneyResponse>> returnJourneyList = new MutableLiveData<>(null);

    private MutableLiveData<Ticket> departureTicket = new MutableLiveData<>(new Ticket());
    private MutableLiveData<Ticket> returnTicket = new MutableLiveData<>(new Ticket());

    public ReservationViewModel(SharedPreferences sharedPrefer){
        this.sharedPrefer = sharedPrefer;
        token = sharedPrefer.getString("token", "");
        reservationApiService = ApiClient.getRetrofitInstance(token).create(ReservationApiService.class);
    }

    public void loadAllProvince() {
        reservationApiService.getAllProvince().enqueue(new Callback<Resource<List<Province>>>() {
            @Override
            public void onResponse(Call<Resource<List<Province>>> call, retrofit2.Response<Resource<List<Province>>> response) {
                if ( response.isSuccessful() ) {
                    provinces.postValue(response.body().getData());
                }
                else {

                }
            }

            @Override
            public void onFailure(Call<Resource<List<Province>>> call, Throwable t) {

            }
        });
    }

    public void loadDepartureJourneyList() {
        JourneyRequest journeyRequest = journey.getValue();
        reservationApiService.findJourney(
                journeyRequest.getOriginProvinceId(),
                journeyRequest.getDestProvinceId(),
                journeyRequest.getStartDate())
                .enqueue(new Callback<Resource<List<JourneyResponse>>>() {
            @Override
            public void onResponse(Call<Resource<List<JourneyResponse>>> call, retrofit2.Response<Resource<List<JourneyResponse>>> response) {
                if (response.isSuccessful()) {
                    departureJourneyList.postValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<Resource<List<JourneyResponse>>> call, Throwable t) {

            }
        });

    }

    public void loadReturnJourneyList() {
        JourneyRequest journeyRequest = journey.getValue();
        reservationApiService.findJourney(
                        journeyRequest.getDestProvinceId(),
                        journeyRequest.getOriginProvinceId(),
                        journeyRequest.getEndDate())
                .enqueue(new Callback<Resource<List<JourneyResponse>>>() {
                    @Override
                    public void onResponse(Call<Resource<List<JourneyResponse>>> call, retrofit2.Response<Resource<List<JourneyResponse>>> response) {
                        if (response.isSuccessful()) {
                            returnJourneyList.postValue(response.body().getData());
                        }
                    }

                    @Override
                    public void onFailure(Call<Resource<List<JourneyResponse>>> call, Throwable t) {

                    }
                });
    }

    public void orderOneWayTicket(String transactionId) {
        Ticket ticket = departureTicket.getValue();
        PaymentOneWayRequest request = new PaymentOneWayRequest(
                1,
                ticket.getSeatNameList(),
                1,
                ticket.getJourney().getId(),
                ticket.getFullName(),
                ticket.getPhoneNumber(),
                ticket.getEmail(),
                transactionId
        );
        reservationApiService.createOrderOneWayTrip(request).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });


    }

    public void orderRoundTripTicket(String transactionId) {

        Ticket departureTicket = this.departureTicket.getValue();
        Ticket returnTicket = this.returnTicket.getValue();

        PaymentTwoWayRequest request = new PaymentTwoWayRequest(
                1,
                departureTicket.getSeatNameList(),
                returnTicket.getSeatNameList(),
                1,
                departureTicket.getJourney().getId(),
                returnTicket.getJourney().getId(),
                departureTicket.getFullName(),
                departureTicket.getPhoneNumber(),
                departureTicket.getEmail(),
                transactionId
        );
        reservationApiService.createOrderRoundTrip(request).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }





    // setter and getter
    public LiveData<Boolean> getIsRoundTrip() {
        return isRoundTrip;
    }

    public void setIsRoundTrip(boolean isRoundTrip) {
        this.isRoundTrip.setValue(isRoundTrip);
    }

    public LiveData<List<Province>> getProvinces() {
        return provinces;
    }

    public LiveData<JourneyRequest> getJourney() {
        return journey;
    }

    public void setJourney(JourneyRequest journey) {
        this.journey.setValue(journey);
    }

    public LiveData<List<JourneyResponse>> getDepartureJourneyList() {
        return departureJourneyList;
    }
    public LiveData<List<JourneyResponse>> getReturnJourneyList() {
        return returnJourneyList;
    }

    public LiveData<Ticket> getDepartureTicket() {
        return departureTicket;
    }

    public void setDepartureTicket(Ticket departureTicket) {
        this.departureTicket.setValue(departureTicket);
    }

    public LiveData<Ticket> getReturnTicket() {
        return returnTicket;
    }

    public void setReturnTicket(Ticket returnTicket) {
        this.returnTicket.setValue(returnTicket);
    }
}
