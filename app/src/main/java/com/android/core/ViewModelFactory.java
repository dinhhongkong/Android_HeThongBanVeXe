package com.android.core;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.feature.home.reservation.ReservationViewModel;
import com.android.feature.login.LoginViewModel;
import com.android.service.AuthApiService;
import com.android.service.api_interface.ApiClient;
import com.android.utils.SharedPref;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private Application context;
    public ViewModelFactory(Application context) {
        this.context = context;
    }
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ReservationViewModel.class)) {
            return (T) new ReservationViewModel(context);
        }
        else if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(context);
        }
        else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
