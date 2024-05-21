package com.android.feature.home.reservation.seat.page;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

public class ReturnSeatFragment extends BaseSeatFragment {
    public static ReturnSeatFragment newInstance() {
        return new ReturnSeatFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAdapter();
    }

    private void initAdapter() {
        bottomSeatAdapter = new SeatAdapter(SeatAdapter.Type.LIMOUSINE, SeatAdapter.Position.BOTTOM);
        binding.rvBottomSeat.setAdapter(bottomSeatAdapter);
        var gridLayoutManager = new GridLayoutManager(requireContext(),3);
        binding.rvBottomSeat.setLayoutManager(new GridLayoutManager(requireContext(),3){
            @Override
            public boolean isAutoMeasureEnabled() {
                return true;
            }
        });

        topSeatAdapter = new SeatAdapter(SeatAdapter.Type.LIMOUSINE, SeatAdapter.Position.TOP);
        binding.rvTopSeat.setAdapter(topSeatAdapter);
        binding.rvTopSeat.setLayoutManager(new GridLayoutManager(requireContext(),3));
    }
}
