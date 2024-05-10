package com.android.feature.home.reservation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.android.R;
import com.android.databinding.FragmentReservationBinding;
import com.android.model.Province;
import com.android.utils.ActionBarUtils;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ReservationFragment extends Fragment {
    private FragmentReservationBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ActionBarUtils.toggle(false);
        binding = FragmentReservationBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupTabLayoutAndViewPager();
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        ActionBarUtils.toggle(true);
        super.onDestroy();
    }

    private void setupTabLayoutAndViewPager(){

    }





    private void goBack() {
        Navigation.findNavController(requireView()).navigate(R.id.action_reservationFragment_to_ticketFragment);
    }
}