package com.android.auth.register;

import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.navigation.Navigation;

import com.android.R;

public class RegisterViewModel extends BaseObservable {
    private String username, password, password2;

    public RegisterViewModel() {
        this.username = "";
        this.password = "";
        this.password2 = "";
    }

    public RegisterViewModel(String username, String password, String password2) {
        this.username = username;
        this.password = password;
        this.password2 = password2;
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

    @Bindable
    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
        notifyPropertyChanged(BR.password2);
    }

    public void register(View view) {
        // CALL API REGISTER & REDIRECT TO LOGIN PAGE.
        Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment);
    }
}
