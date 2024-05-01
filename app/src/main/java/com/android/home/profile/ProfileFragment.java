package com.android.home.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.android.MainActivity;
import com.android.R;
import com.android.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding mFragmentProfileBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MainActivity.setActionBarTitle(getActivity(), "THÔNG TIN CÁ NHÂN");

        this.mFragmentProfileBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_profile, container, false);
        ProfileViewModel profileViewModel = new ProfileViewModel();
        mFragmentProfileBinding.setProfileViewModel(profileViewModel);

        return mFragmentProfileBinding.getRoot();
    }
}