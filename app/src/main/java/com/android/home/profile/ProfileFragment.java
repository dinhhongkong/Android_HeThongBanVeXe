package com.android.home.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.android.R;
import com.android.utils.ActionBarUtils;

public class ProfileFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ActionBarUtils.setTitle("THÔNG TIN CÁ NHÂN");

        com.android.databinding.FragmentProfileBinding mFragmentProfileBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_profile, container, false);
        ProfileViewModel profileViewModel = new ProfileViewModel();
        mFragmentProfileBinding.setProfileViewModel(profileViewModel);

        return mFragmentProfileBinding.getRoot();
    }
}