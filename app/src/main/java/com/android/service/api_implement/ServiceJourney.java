package com.android.service.api_implement;

import androidx.annotation.NonNull;

import com.android.home.reservation.journey.AdapterJourney;
import com.android.model.Province;
import com.android.model.response.JourneyResponse;
import com.android.service.APIResponse;
import com.android.service.api_interface.APIJourney;
import com.android.utils.DateUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceJourney {
    public static void listJourney(int originId, int destinationId, String startDate, AdapterJourney adapter) {
        APIJourney.service.listJourney(originId, destinationId, startDate).enqueue(new Callback<APIResponse<JsonArray>>() {
            @Override
            public void onResponse(@NonNull Call<APIResponse<JsonArray>> call, @NonNull Response<APIResponse<JsonArray>> response) {
                if (!response.isSuccessful() || response.body() == null) return;
                adapter.setData(response.body().getData().asList()
                        .stream()
                        .map(JsonElement::getAsJsonObject)
                        .map(e -> {
                            String datePart = e.get("ngayKhoiHanh").getAsString();
                            String timePart = e.get("gioXuatPhat").getAsString();
                            double duration = e.get("thoiGianDuKien").getAsDouble();
                            double price = e.get("gia").getAsDouble();
                            int originId = e.get("maTinhDi").getAsInt();
                            int destinationId = e.get("maTinhDen").getAsInt();
                            String originName = e.get("tenTinhDi").getAsString();
                            String destinationName = e.get("tenTinhDen").getAsString();
                            List<String> listReversedSeat = e.get("listMaGhe").getAsJsonArray()
                                    .asList().stream()
                                    .map(JsonElement::getAsString)
                                    .collect(Collectors.toList());

                            JourneyResponse item = new JourneyResponse();
                            item.setOrigin(new Province(originId, originName));
                            item.setDestination(new Province(destinationId, destinationName));
                            item.setStartTime(datePart + " " + timePart);
                            item.setEndTime(DateUtils.getCalculatedDateString(datePart, timePart, duration));
                            item.setListReservedSeat(listReversedSeat);
                            item.setDuration(duration);
                            item.setPrice(price);
                            return item;
                        })
                        .collect(Collectors.toList()));
            }

            @Override
            public void onFailure(@NonNull Call<APIResponse<JsonArray>> call, @NonNull Throwable t) {

            }
        });
    }
}
