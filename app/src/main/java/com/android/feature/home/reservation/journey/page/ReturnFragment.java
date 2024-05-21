package com.android.feature.home.reservation.journey.page;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.model.Ticket;

public class ReturnFragment extends BaseJourneyFragment {
    public static ReturnFragment newInstance() {
        return new ReturnFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.loadReturnJourneyList();
    }

    @Override
    protected void initJourneyAdapter() {
        viewModel.getReturnJourneyList().observe(getViewLifecycleOwner(),item->{
            journeyAdapter = new JourneyAdapter();
            binding.rvJourney.setAdapter(journeyAdapter);
            binding.rvJourney.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
            journeyAdapter.submitList(viewModel.getReturnJourneyList().getValue());
            journeyAdapter.setOnItemClickListener(journey -> {
                Ticket ticket = new Ticket();
                ticket.setJourney(journey);
                viewModel.setReturnTicket(ticket);
            });
        });
    }
}
