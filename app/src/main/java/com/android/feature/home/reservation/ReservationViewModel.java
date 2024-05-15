package com.android.feature.home.reservation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.model.Province;
import com.android.model.request.JourneyRequest;
import com.android.service.ReservationApiService;
import com.android.service.Response;
import com.android.service.api_interface.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ReservationViewModel extends ViewModel {

    private ReservationApiService reservationApiService;
    private MutableLiveData<Boolean> isRoundTrip = new MutableLiveData<>(false);
    private MutableLiveData<List<Province>> provinces = new MutableLiveData<>(null);
    private MutableLiveData<JourneyRequest> journey = new MutableLiveData<>(new JourneyRequest(0,0,"",false));

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
}
