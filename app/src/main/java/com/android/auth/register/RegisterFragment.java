package com.android.auth.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.android.R;
import com.android.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentRegisterBinding mFragmentRegisterBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_register, container, false);
        RegisterViewModel registerViewModel = new RegisterViewModel();
        mFragmentRegisterBinding.setRegisterViewModel(registerViewModel);

        return mFragmentRegisterBinding.getRoot();
    }
}