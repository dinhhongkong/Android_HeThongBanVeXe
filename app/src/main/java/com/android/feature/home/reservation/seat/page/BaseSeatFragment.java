package com.android.feature.home.reservation.seat.page;

import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.databinding.FragmentSeatBinding;
import com.android.feature.home.reservation.ReservationViewModel;
import com.android.feature.home.reservation.seat.page.SeatAdapter;
import com.android.model.Seat;

import java.util.List;
import java.util.Set;

public abstract class BaseSeatFragment extends Fragment {

    protected FragmentSeatBinding binding;
    protected ReservationViewModel viewModel;
    protected SeatAdapter bottomSeatAdapter;
    protected SeatAdapter topSeatAdapter;
    @Override
    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ReservationViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSeatBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    public List<Seat> setSelectedSeatByUser(Set<String> selectedSeat, List<Seat> seats) {
        for (String seat : selectedSeat) {
            for(Seat item : seats) {
                if(item.getName().equals(seat)) {
                    item.setStatus(2);
                }
            }
        }
        return seats;
    }




//        private void initAdapter() {
//        TopDeskSeatAdapter adapter = new TopDeskSeatAdapter();
//        binding.rvRecentlySearch.setAdapter(adapter);
//        binding.rvRecentlySearch.setLayoutManager(new GridLayoutManager(requireContext(),5) {
//
//        });
//    }
}