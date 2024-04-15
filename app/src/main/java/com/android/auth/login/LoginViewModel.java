package com.android.auth.login;

import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.navigation.Navigation;

import com.android.BR;
import com.android.R;

public class LoginViewModel extends BaseObservable {
    private String username, password;

    public LoginViewModel() {
        this.username = "";
        this.password = "";
    }

    public LoginViewModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Bindable
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username.trim();
        notifyPropertyChanged(BR.username);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    public void redirectToRegisterPage(View view) {
        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment);
    }

    public void login(View view) {
        // CALL API LOGIN & REDIRECT TO HOME PAGE.
        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment); // DEBUG.
    }
}
