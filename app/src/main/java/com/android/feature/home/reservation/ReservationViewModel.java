package com.android.feature.home.reservation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.model.Province;
import com.android.model.Ticket;
import com.android.model.request.JourneyRequest;
import com.android.model.response.JourneyResponse;
import com.android.service.ReservationApiService;
import com.android.core.Response;
import com.android.service.api_interface.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ReservationViewModel extends ViewModel {

    private ReservationApiService reservationApiService;
    private MutableLiveData<Boolean> isRoundTrip = new MutableLiveData<>(false);
    private MutableLiveData<List<Province>> provinces = new MutableLiveData<>(null);
    private MutableLiveData<JourneyRequest> journey = new MutableLiveData<>(new JourneyRequest());
    private MutableLiveData<List<JourneyResponse>> departureJourneyList = new MutableLiveData<>(null);
    private MutableLiveData<List<JourneyResponse>> returnJourneyList = new MutableLiveData<>(null);

    private MutableLiveData<Ticket> departureTicket = new MutableLiveData<>(new Ticket());
    private MutableLiveData<Ticket> returnTicket = new MutableLiveData<>(new Ticket());

    public ReservationViewModel(){
        reservationApiService = ApiClient.getRetrofitInstance().create(ReservationApiService.class);
    }

    public void loadAllProvince() {
        reservationApiService.getAllProvince().enqueue(new Callback<Response<List<Province>>>() {
            @Override
            public void onResponse(Call<Response<List<Province>>> call, retrofit2.Response<Response<List<Province>>> response) {
                if ( response.isSuccessful() ) {
                    provinces.postValue(response.body().getData());
                }
                else {

                }
            }

            @Override
            public void onFailure(Call<Response<List<Province>>> call, Throwable t) {

            }
        });
    }

    public void loadDepartureJourneyList() {
        JourneyRequest journeyRequest = journey.getValue();
        reservationApiService.findJourney(
                journeyRequest.getOriginProvinceId(),
                journeyRequest.getDestProvinceId(),
                journeyRequest.getStartDate())
                .enqueue(new Callback<Response<List<JourneyResponse>>>() {
            @Override
            public void onResponse(Call<Response<List<JourneyResponse>>> call, retrofit2.Response<Response<List<JourneyResponse>>> response) {
                if (response.isSuccessful()) {
                    departureJourneyList.postValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<Response<List<JourneyResponse>>> call, Throwable t) {

            }
        });

    }

    public void loadReturnJourneyList() {
        JourneyRequest journeyRequest = journey.getValue();
        reservationApiService.findJourney(
                        journeyRequest.getDestProvinceId(),
                        journeyRequest.getOriginProvinceId(),
                        journeyRequest.getEndDate())
                .enqueue(new Callback<Response<List<JourneyResponse>>>() {
                    @Override
                    public void onResponse(Call<Response<List<JourneyResponse>>> call, retrofit2.Response<Response<List<JourneyResponse>>> response) {
                        if (response.isSuccessful()) {
                            returnJourneyList.postValue(response.body().getData());
                        }
                    }

                    @Override
                    public void onFailure(Call<Response<List<JourneyResponse>>> call, Throwable t) {

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
