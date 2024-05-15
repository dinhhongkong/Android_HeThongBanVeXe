package com.android.feature.home.reservation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.android.R;
import com.android.databinding.FragmentReservationBinding;
import com.android.utils.ActionBarUtils;
import com.google.android.material.tabs.TabLayoutMediator;

public class ReservationFragment extends Fragment {
    private FragmentReservationBinding binding;
    private ReservationViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ReservationViewModel.class);
    }

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
        ReservationViewPagerAdapter viewPagerAdapter = new ReservationViewPagerAdapter(this,true);
        binding.viewPager.setAdapter(viewPagerAdapter);
        new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> {

            if(position == 0) {
                tab.setText("Ngày đi: 20/04/2024");
            }
            else {
                tab.setText("Ngày về: 25/04/2024");
            }

        }).attach();
    }





    private void goBack() {
        Navigation.findNavController(requireView()).navigate(R.id.action_reservationFragment_to_ticketFragment);
    }
}