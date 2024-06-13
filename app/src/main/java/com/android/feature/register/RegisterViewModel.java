package com.android.feature.register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.model.request.RegisterRequest;
import com.android.payload.Resource;
import com.android.service.AuthApiService;
import com.android.service.RegisterService;
import com.android.service.api_interface.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends ViewModel {
    private RegisterService registerService;
    private MutableLiveData<RegisterRequest> registerRequest = new MutableLiveData<>(new RegisterRequest("",""));

    private MutableLiveData<String> message = new MutableLiveData<>("");

    public RegisterViewModel() {
        registerService =  ApiClient.getRetrofitInstance().create(RegisterService.class);
    }

    public LiveData<String> getMessage() {
        return message;
    }
    public LiveData<RegisterRequest> getRegisterRequest() {
        return registerRequest;
    }

    public void setRegisterRequest(RegisterRequest registerRequest) {
        this.registerRequest.setValue(registerRequest);
    }

    public void registerNewUser() {
        registerService.registerNewAccount(registerRequest.getValue()).enqueue(new Callback<Resource<String>>() {
            @Override
            public void onResponse(Call<Resource<String>> call, Response<Resource<String>> response) {
                message.postValue("Đăng kí thành công");
            }

            @Override
            public void onFailure(Call<Resource<String>> call, Throwable t) {
                message.postValue("Đăng kí thất bại, vui lòng kiểm tra lại");
            }
        });
    }

}
