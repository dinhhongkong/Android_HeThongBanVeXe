package com.android.home.ticket;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.android.R;
import com.android.databinding.FragmentBookTicketBinding;

public class BookTicketFragment extends Fragment {
    private FragmentBookTicketBinding mFragmentBookTicketBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mFragmentBookTicketBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_book_ticket, container, false);
        BookTicketModelView bookTicketModelView = new BookTicketModelView();
        mFragmentBookTicketBinding.setBookTicketModelView(bookTicketModelView);

        return mFragmentBookTicketBinding.getRoot();
    }
}