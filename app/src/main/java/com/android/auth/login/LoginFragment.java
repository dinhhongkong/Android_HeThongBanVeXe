package com.android.auth.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.android.R;
import com.android.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentLoginBinding mFragmentLoginBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_login, container, false);
        LoginViewModel loginViewModel = new LoginViewModel();
        mFragmentLoginBinding.setLoginViewModel(loginViewModel);

        return mFragmentLoginBinding.getRoot();
    }
}