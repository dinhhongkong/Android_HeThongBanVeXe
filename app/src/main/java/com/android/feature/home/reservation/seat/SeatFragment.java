package com.android.feature.home.reservation.seat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.databinding.FragmentSeatBinding;

public class SeatFragment extends Fragment {

    protected FragmentSeatBinding binding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

//        private void initAdapter() {
//        TopDeskSeatAdapter adapter = new TopDeskSeatAdapter();
//        binding.rvRecentlySearch.setAdapter(adapter);
//        binding.rvRecentlySearch.setLayoutManager(new GridLayoutManager(requireContext(),5) {
//
//        });
//    }
}