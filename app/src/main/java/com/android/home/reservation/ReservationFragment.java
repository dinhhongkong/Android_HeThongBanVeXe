package com.android.home.reservation;

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
//        receiveData();
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
        TabLayout tabLayout = binding.TabLayout;
        ViewPager2 viewPager2 = binding.ViewPager;

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(fragmentManager, getLifecycle());
//        viewPagerAdapter.setData(searchData);
        viewPager2.setAdapter(viewPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, i) -> {
            tab.setText(viewPagerAdapter.getPageTitle(i));
        }).attach();
    }





    private void goBack() {
        View root = getView();
        if (root == null) return;
        Navigation.findNavController(root).navigate(R.id.action_reservationFragment_to_ticketFragment);
    }
}