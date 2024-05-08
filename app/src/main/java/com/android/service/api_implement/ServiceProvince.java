package com.android.service.api_implement;

import androidx.annotation.NonNull;

import com.android.model.Province;
import com.android.service.APIResponse;
import com.android.service.api_interface.APIProvince;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceProvince {
    public static void listProvince(List<Province> dataStorage) {
        APIProvince.service.listProvince().enqueue(new Callback<APIResponse<JsonArray>>() {
            @Override
            public void onResponse(@NonNull Call<APIResponse<JsonArray>> call, @NonNull Response<APIResponse<JsonArray>> response) {
                if (!response.isSuccessful() || response.body() == null) return;
                dataStorage.addAll(response.body().getData().asList()
                        .stream()
                        .map(JsonElement::getAsJsonObject)
                        .map(e -> new Province(e.get("maTinh").getAsInt(), e.get("tenTinh").getAsString().toUpperCase()))
                        .collect(Collectors.toList()));
            }

            @Override
            public void onFailure(@NonNull Call<APIResponse<JsonArray>> call, @NonNull Throwable t) {

            }
        });
    }
}
