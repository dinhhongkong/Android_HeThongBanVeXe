package com.android.feature.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment {
    private com.android.databinding.FragmentRegisterBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = com.android.databinding.FragmentRegisterBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}