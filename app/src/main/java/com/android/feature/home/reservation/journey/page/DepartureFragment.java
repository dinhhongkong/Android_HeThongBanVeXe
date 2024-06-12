package com.android.feature.home.reservation.journey.page;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.model.Ticket;

public class DepartureFragment extends BaseJourneyFragment {
    public static DepartureFragment newInstance() {
        return new DepartureFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.loadDepartureJourneyList();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    protected void initJourneyAdapter() {
        viewModel.getDepartureJourneyList().observe(getViewLifecycleOwner(),item->{
            journeyAdapter = new JourneyAdapter();
            binding.rvJourney.setAdapter(journeyAdapter);
            binding.rvJourney.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
            journeyAdapter.submitList(viewModel.getDepartureJourneyList().getValue());
            journeyAdapter.setOnItemClickListener(journey -> {
                Ticket ticket = new Ticket();
                ticket.setJourney(journey);
                viewModel.setDepartureTicket(ticket);
            });
        });

    }


}