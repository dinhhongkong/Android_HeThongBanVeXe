package com.android.service.api_implement;

import androidx.annotation.NonNull;

import com.android.home.reservation.journey.AdapterJourney;
import com.android.service.APIResponse;
import com.android.model.Province;
import com.android.model.response.JourneyResponse;
import com.android.service.api_interface.APIJourney;
import com.android.utils.DateUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceJourney {
    public static void listJourney(int originId, int destinationId, String startDate, AdapterJourney adapter) {
        APIJourney.service.listJourney(originId, destinationId, startDate).enqueue(new Callback<APIResponse<JsonArray>>() {
            @Override
            public void onResponse(@NonNull Call<APIResponse<JsonArray>> call, @NonNull Response<APIResponse<JsonArray>> response) {
                if (!response.isSuccessful() || response.body() == null) return;
                adapter.setData(extractData(response.body().getData()));
            }

            @Override
            public void onFailure(@NonNull Call<APIResponse<JsonArray>> call, @NonNull Throwable t) {

            }
        });
    }

    private static List<JourneyResponse> extractData(JsonArray responseData) {
        List<JourneyResponse> dataStorage = new ArrayList<>();
        responseData.forEach(e -> {
            JsonObject data = e.getAsJsonObject();
            String datePart = data.get("ngayKhoiHanh").getAsString();
            String timePart = data.get("gioXuatPhat").getAsString();
            double duration = data.get("thoiGianDuKien").getAsDouble();
            int originId = data.get("maTinhDi").getAsInt();
            String originName = data.get("tenTinhDi").getAsString();
            int destinationId = data.get("maTinhDen").getAsInt();
            String destinationName = data.get("tenTinhDen").getAsString();
            double price = data.get("gia").getAsDouble();

            JourneyResponse journeyResponse = new JourneyResponse();
            journeyResponse.setOrigin(new Province(originId, originName));
            journeyResponse.setDestination(new Province(destinationId, destinationName));
            journeyResponse.setStartTime(datePart + " " + timePart);
            journeyResponse.setEndTime(DateUtils.getCalculatedDateString(datePart, timePart, duration));
            journeyResponse.setListReservedSeat(new ArrayList<>());
            journeyResponse.setDuration(duration);
            journeyResponse.setPrice(price);

            dataStorage.add(journeyResponse);
        });
        return dataStorage;
    }
}
