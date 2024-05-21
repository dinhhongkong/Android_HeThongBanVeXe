package com.android.feature.home.reservation.seat.page;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

public class DepartureSeatFragment extends BaseSeatFragment{
    public static DepartureSeatFragment newInstance() {
        return new DepartureSeatFragment();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAdapter();
    }

    private void initAdapter() {
        bottomSeatAdapter = new SeatAdapter(SeatAdapter.Type.BOTTOM_SLEEPING_BERTH, SeatAdapter.Position.BOTTOM);
        binding.rvBottomSeat.setAdapter(bottomSeatAdapter);
        binding.rvBottomSeat.setLayoutManager(new GridLayoutManager(requireContext(),5));

        topSeatAdapter = new SeatAdapter(SeatAdapter.Type.TOP_SLEEPING_BERTH, SeatAdapter.Position.TOP);
        binding.rvTopSeat.setAdapter(topSeatAdapter);
        binding.rvTopSeat.setLayoutManager(new GridLayoutManager(requireContext(),5));
    }
}
