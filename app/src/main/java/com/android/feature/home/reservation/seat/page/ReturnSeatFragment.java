package com.android.feature.home.reservation.seat.page;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.android.model.Seat;

import java.util.List;

public class ReturnSeatFragment extends BaseSeatFragment {
    public static ReturnSeatFragment newInstance() {
        return new ReturnSeatFragment();
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
            viewModel.getReturnTicket().getValue().getSeatNameList().remove(seat.getName());
            seat.setStatus(0);
            return;
        }
        if (viewModel.getReturnTicket().getValue().getSeatNameList().size() == 5) {
            Toast.makeText(requireContext(), "Chỉ được chọn tối đa 5 ghế", Toast.LENGTH_SHORT).show();
            return;
        }
        if(seat.getStatus()==0) {
            seat.setStatus(2);
            viewModel.getReturnTicket().getValue().getSeatNameList().add(seat.getName());
        }

    }

    private void initAdapter() {
//        bottomSeatAdapter = new SeatAdapter(SeatAdapter.Type.LIMOUSINE, SeatAdapter.Position.BOTTOM, viewModel.getReturnTicket().getValue().getJourney().getListIdSeat());
//        binding.rvBottomSeat.setAdapter(bottomSeatAdapter);
//        binding.rvBottomSeat.setLayoutManager(new GridLayoutManager(requireContext(),3));
//
//        topSeatAdapter = new SeatAdapter(SeatAdapter.Type.LIMOUSINE, SeatAdapter.Position.TOP, viewModel.getReturnTicket().getValue().getJourney().getListIdSeat());
//        binding.rvTopSeat.setAdapter(topSeatAdapter);
//        binding.rvTopSeat.setLayoutManager(new GridLayoutManager(requireContext(),3));

        bottomSeatAdapter = new SeatAdapter(SeatAdapter.Type.LIMOUSINE, SeatAdapter.Position.BOTTOM, viewModel.getReturnTicket().getValue().getJourney().getListIdSeat());
        binding.rvBottomSeat.setAdapter(bottomSeatAdapter);
        binding.rvBottomSeat.setLayoutManager(new GridLayoutManager(requireContext(),5));
        List<Seat> bottomSeats = setSelectedSeatByUser(viewModel.getReturnTicket().getValue().getSeatNameList(), bottomSeatAdapter.getListOfSeat());
        bottomSeatAdapter.submitList(bottomSeats);

        topSeatAdapter = new SeatAdapter(SeatAdapter.Type.LIMOUSINE, SeatAdapter.Position.TOP, viewModel.getReturnTicket().getValue().getJourney().getListIdSeat());
        List<Seat> topSeats = setSelectedSeatByUser(viewModel.getReturnTicket().getValue().getSeatNameList(), topSeatAdapter.getListOfSeat());
        topSeatAdapter.submitList(topSeats);
        binding.rvTopSeat.setAdapter(topSeatAdapter);
        binding.rvTopSeat.setLayoutManager(new GridLayoutManager(requireContext(),5));
    }
}
