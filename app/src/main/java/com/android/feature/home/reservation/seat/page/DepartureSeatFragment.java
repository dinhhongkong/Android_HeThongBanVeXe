package com.android.feature.home.reservation.seat.page;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.android.model.Seat;

import java.util.List;
import java.util.Set;

public class DepartureSeatFragment extends BaseSeatFragment{
    public static DepartureSeatFragment newInstance() {
        return new DepartureSeatFragment();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAdapter();
        setEvent();
    }



    private void setEvent() {
        bottomSeatAdapter.setOnItemClickListener(this::selectSeat);

        topSeatAdapter.setOnItemClickListener(this::selectSeat);
    }

    private void selectSeat(Seat seat) {
        if (seat.getStatus()==2) {
            viewModel.getDepartureTicket().getValue().getSeatNameList().remove(seat.getName());
            seat.setStatus(0);
            return;
        }
        if (viewModel.getDepartureTicket().getValue().getSeatNameList().size() == 5) {
            Toast.makeText(requireContext(), "Chỉ được chọn tối đa 5 ghế", Toast.LENGTH_SHORT).show();
            return;
        }
        if(seat.getStatus()==0) {
            seat.setStatus(2);
            viewModel.getDepartureTicket().getValue().getSeatNameList().add(seat.getName());
        }

    }

    private void initAdapter() {
        bottomSeatAdapter = new SeatAdapter(SeatAdapter.Type.LIMOUSINE, SeatAdapter.Position.BOTTOM, viewModel.getDepartureTicket().getValue().getJourney().getListIdSeat());
        binding.rvBottomSeat.setAdapter(bottomSeatAdapter);
        binding.rvBottomSeat.setLayoutManager(new GridLayoutManager(requireContext(),5));
        List<Seat> bottomSeats = setSelectedSeatByUser(viewModel.getDepartureTicket().getValue().getSeatNameList(), bottomSeatAdapter.getListOfSeat());
        bottomSeatAdapter.submitList(bottomSeats);

        topSeatAdapter = new SeatAdapter(SeatAdapter.Type.LIMOUSINE, SeatAdapter.Position.TOP, viewModel.getDepartureTicket().getValue().getJourney().getListIdSeat());
        List<Seat> topSeats = setSelectedSeatByUser(viewModel.getDepartureTicket().getValue().getSeatNameList(), topSeatAdapter.getListOfSeat());
        topSeatAdapter.submitList(topSeats);
        binding.rvTopSeat.setAdapter(topSeatAdapter);
        binding.rvTopSeat.setLayoutManager(new GridLayoutManager(requireContext(),5));
    }


}
