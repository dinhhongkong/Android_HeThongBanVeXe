package com.android.feature.login;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.model.request.LoginRequest;
import com.android.payload.Resource;
import com.android.service.AuthApiService;
import com.android.service.api_interface.ApiClient;
import com.android.utils.JwtUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {
    private AuthApiService authApiService;
    private Application application;
    private String token = "";
    private MutableLiveData<LoginStatus> loginStatus = new MutableLiveData<>(LoginStatus.NONE);


    private MutableLiveData<LoginRequest> loginRequest = new MutableLiveData<>();

    public LoginViewModel( Application application) {
        this.application = application;
        authApiService = ApiClient.getRetrofitInstance(token).create(AuthApiService.class);
    }

    public void userLogin() {
        authApiService.login(loginRequest.getValue()).enqueue(new Callback<Resource<String>>() {
            @Override
            public void onResponse(Call<Resource<String>> call, Response<Resource<String>> response) {
                if (response.isSuccessful()) {
                    token = response.body().getData();
                    JwtUtils.saveToken(application,token);
                    loginStatus.setValue(LoginStatus.SUCCESS);
                    loginRequest.setValue(new LoginRequest());
                }
                else {
                    loginStatus.postValue(LoginStatus.ERROR);
                }
            }

            @Override
            public void onFailure(Call<Resource<String>> call, Throwable t) {

            }
        });
    }

    public void validateToken() {

        if ( !JwtUtils.getToken(application).isEmpty()) {
            token = JwtUtils.getToken(application);
            Log.d("token",token);
//            loginStatus.postValue(LoginStatus.AUTHORIZED);
        }
        else {
            return;
        }
        authApiService.validate(token).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    loginStatus.postValue(LoginStatus.SUCCESS);
                }
                else {
                    loginStatus.postValue(LoginStatus.UNAUTHORIZED);
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }


    public LiveData<LoginRequest> getLoginRequest() {
        return loginRequest;
    }

    public void setLoginRequest(LoginRequest loginRequest) {
        this.loginRequest.setValue(loginRequest);
    }

    public LiveData<LoginStatus> getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(LoginStatus loginStatus) {
        this.loginStatus.setValue(loginStatus);
    }
}
