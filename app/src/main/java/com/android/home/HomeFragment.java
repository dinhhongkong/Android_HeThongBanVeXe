package com.android.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.android.R;
import com.android.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentHomeBinding mFragmentHomeBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_home, container, false);
        HomeModelView homeModelView = new HomeModelView();
        mFragmentHomeBinding.setHomeModelView(homeModelView);

        return mFragmentHomeBinding.getRoot();
    }
}