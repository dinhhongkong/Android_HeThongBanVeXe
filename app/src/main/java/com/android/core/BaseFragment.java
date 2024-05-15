package com.android.core;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.android.databinding.FragmentLoginBinding;

public abstract class BaseFragment<VB extends ViewBinding> extends Fragment {
    private VB binding;
    protected VB getBinding() {
        return binding;
    }


//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        binding = VB.inflate(inflater,container,false);
//        return binding.getRoot();
//    }
}
