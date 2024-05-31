package com.android.feature.home.reservation.seat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.R;
import com.android.databinding.FragmentSeatSelectionBinding;
import com.android.feature.home.reservation.ReservationViewModel;
import com.android.model.Ticket;
import com.android.utils.ActionBarUtils;
import com.android.utils.DateUtils;
import com.google.android.material.tabs.TabLayoutMediator;


public class SeatSelectionFragment extends Fragment {

    private FragmentSeatSelectionBinding binding;
    private ReservationViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ReservationViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ActionBarUtils.toggle(false);
        binding = FragmentSeatSelectionBinding.inflate(inflater, container, false);
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
        SeatSelectionViewPageAdapter viewPagerAdapter = new SeatSelectionViewPageAdapter(this, viewModel.getIsRoundTrip().getValue());
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

            } else {

            }

        });

        viewModel.getReturnTicket().observe(getViewLifecycleOwner(), ticket -> {
            if (ticket.getJourney() != null) {

            } else {

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

        binding.btnContinue.setOnClickListener(v->{
            goToCustomerInfoScreen();
        });
    }


    private void goBack() {
        Navigation.findNavController(requireView()).navigate(R.id.action_reservationFragment_to_ticketFragment);
    }

    private void goToCustomerInfoScreen() {
        if(viewModel.getDepartureTicket().getValue().getSeatNameList().isEmpty()) {
            Toast.makeText(requireContext(), "Vui lòng chọn ít nhất 1 ghế tại chuyến khởi hành", Toast.LENGTH_SHORT).show();
            return ;
        }
        if(viewModel.getReturnTicket().getValue().getSeatNameList().isEmpty() && viewModel.getIsRoundTrip().getValue()) {
            Toast.makeText(requireContext(), "Vui lòng chọn chọn ít nhất 1 ghế tại chuyến khứ hồi", Toast.LENGTH_SHORT).show();
            return ;
        }
        Navigation.findNavController(requireView()).navigate(R.id.action_seatSelectionFragment_to_customerInfoFragment);
    }
}