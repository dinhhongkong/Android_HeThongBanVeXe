package com.android.feature.home.reservation.journey;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.databinding.FragmentJourneyBinding;
import com.android.feature.home.reservation.ReservationViewModel;

public abstract class BaseJourneyFragment extends Fragment {
    protected FragmentJourneyBinding binding;
    protected ReservationViewModel viewModel;
    protected JourneyAdapter journeyAdapter;

    @Override
    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ReservationViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentJourneyBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    @CallSuper
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initJourneyAdapter();
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

     protected abstract void initJourneyAdapter() ;
}
