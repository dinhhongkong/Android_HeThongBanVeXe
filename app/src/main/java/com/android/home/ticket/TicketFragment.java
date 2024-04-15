package com.android.home.ticket;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.android.R;
import com.android.databinding.FragmentTicketBinding;

public class TicketFragment extends Fragment {
    private FragmentTicketBinding mFragmentTicketBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.mFragmentTicketBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_ticket, container, false);
        TicketViewModel ticketViewModel = new TicketViewModel();
        mFragmentTicketBinding.setTicketViewModel(ticketViewModel);

        return mFragmentTicketBinding.getRoot();
    }
}