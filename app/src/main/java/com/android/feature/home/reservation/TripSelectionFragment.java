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
import com.android.databinding.FragmentTripSelectionBinding;
import com.android.model.Ticket;
import com.android.utils.ActionBarUtils;
import com.android.utils.DateUtils;
import com.google.android.material.tabs.TabLayoutMediator;

public class TripSelectionFragment extends Fragment {
    private FragmentTripSelectionBinding binding;
    private ReservationViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ReservationViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ActionBarUtils.toggle(false);
        binding = FragmentTripSelectionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupTabLayoutAndViewPager();
        setupObserver();
        setEvent();
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

    private void setupTabLayoutAndViewPager() {
        TripSelectionViewPagerAdapter viewPagerAdapter = new TripSelectionViewPagerAdapter(this, viewModel.getIsRoundTrip().getValue());
        binding.viewPager.setAdapter(viewPagerAdapter);
        new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> {

            if (position == 0) {
                String date = viewModel.getJourney().getValue().getStartDate();
                date = DateUtils.convertToDDMMYYYY(date);
                tab.setText("Ngày đi: " + date);
            } else {
                String date = viewModel.getJourney().getValue().getEndDate();
                date = DateUtils.convertToDDMMYYYY(date);
                tab.setText("Ngày về: " + date);
            }

        }).attach();
    }

    private void setupObserver() {
        viewModel.getDepartureTicket().observe(getViewLifecycleOwner(), ticket -> {
            if (ticket.getJourney() != null) {
                binding.cvInfoDeparture.setVisibility(View.VISIBLE);
                binding.tvInfoDepartureDate.setText(DateUtils.convertToDDMMYYYY(ticket.getJourney().getStartDate()) );
                binding.tvInfoStartTimeDeparture.setText(ticket.getJourney().getStartTime());
                binding.tvInfoEndTimeDeparture.setText(ticket.getJourney().getStartTime());
                binding.tvInfoDepature.setText(ticket.getJourney().getDepartureProvince());
                binding.tvInfoDestination.setText(ticket.getJourney().getDestProvince());
            } else {
                binding.cvInfoDeparture.setVisibility(View.GONE);
            }

        });

        viewModel.getReturnTicket().observe(getViewLifecycleOwner(), ticket -> {
            if (ticket.getJourney() != null) {
                binding.cvInfoReturn.setVisibility(View.VISIBLE);
                binding.tvInfoReturnDate.setText(DateUtils.convertToDDMMYYYY(ticket.getJourney().getStartDate()) );
                binding.tvInfoStartTimeReturn.setText(ticket.getJourney().getStartTime());
                binding.tvInfoEndTimeReturn.setText(ticket.getJourney().getStartTime());
                binding.tvInfoReturn.setText(ticket.getJourney().getDepartureProvince());
                binding.tvInfoReturnDestination.setText(ticket.getJourney().getDestProvince());
            } else {
                binding.cvInfoReturn.setVisibility(View.GONE);
            }
        });

    }

    private void setEvent() {
        binding.btnDeleteDeparture.setOnClickListener(v->{
            viewModel.setDepartureTicket(new Ticket());
        });
        binding.btnDeleteReturn.setOnClickListener(v->{
            viewModel.setReturnTicket(new Ticket());
        });

        binding.btnSelectSeat.setOnClickListener(v->{
            goToSelectSeat();
        });
    }


    private void goBack() {
        Navigation.findNavController(requireView()).navigate(R.id.action_reservationFragment_to_ticketFragment);
    }

    private void goToSelectSeat() {
        Navigation.findNavController(requireView()).navigate(R.id.action_reservationFragment_to_seatSelectionFragment);
    }
}