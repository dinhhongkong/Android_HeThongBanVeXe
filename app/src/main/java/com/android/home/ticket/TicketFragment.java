package com.android.home.ticket;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.android.MainActivity;
import com.android.R;
import com.android.databinding.FragmentTicketBinding;

import java.util.HashMap;
import java.util.Map;

public class TicketFragment extends Fragment implements View.OnTouchListener, View.OnFocusChangeListener, View.OnClickListener {
    private final Map<String, Integer> PROVINCES;
    private final ArrayAdapter<String> PROVINCE_ADAPTER;
    private FragmentTicketBinding mFragmentTicketBinding;

    {
        this.PROVINCES = new HashMap<>();
        this.PROVINCE_ADAPTER = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line);
        // CALL API GET PROVINCE DATA.
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MainActivity.setActionBarTitle(getActivity(), "ĐẶT VÉ XE");

        this.mFragmentTicketBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_ticket, container, false);
        TicketViewModel ticketViewModel = new TicketViewModel();
        ticketViewModel.setTicketType(0);
        mFragmentTicketBinding.setTicketViewModel(ticketViewModel);

        return mFragmentTicketBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // SETUP AUTOCOMPLETE TEXTVIEW.
        setData();
        setEvent();
    }

    private void setData() {
        mFragmentTicketBinding.actStartDestination.setAdapter(PROVINCE_ADAPTER);
        mFragmentTicketBinding.actEndDestination.setAdapter(PROVINCE_ADAPTER);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setEvent() {
        AutoCompleteTextView actStartDestination = mFragmentTicketBinding.actStartDestination;
        AutoCompleteTextView actEndDestination = mFragmentTicketBinding.actEndDestination;
        // SHOW DROPDOWN LIST EVENT.
        actStartDestination.setOnTouchListener(this);
        actEndDestination.setOnTouchListener(this);
        // TYPE DESTINATION EVENT.
        actStartDestination.setOnFocusChangeListener(this);
        actEndDestination.setOnFocusChangeListener(this);
        // CHOOSE DESTINATION EVENT.
        actStartDestination.setOnItemClickListener((parent, view, position, id) ->
                onItemClick(parent, position, R.id.actStartDestination));
        actEndDestination.setOnItemClickListener((parent, view, position, id) ->
                onItemClick(parent, position, R.id.actEndDestination));
        // SUBMIT EVENT.
        mFragmentTicketBinding.btnSubmit.setOnClickListener(this);
    }

    private AutoCompleteTextView getAutoCompleteTextView(int viewId) {
        if (viewId == R.id.actStartDestination) return mFragmentTicketBinding.actStartDestination;
        else return mFragmentTicketBinding.actEndDestination;
    }

    private void setDestination(AutoCompleteTextView view, String provinceName) {
        TicketViewModel viewModel = mFragmentTicketBinding.getTicketViewModel();
        // CHECK DESTINATION.
        Integer destinationId = PROVINCES.get(provinceName);
        if (destinationId == null) view.setText("");
        // SET DESTINATION.
        if (view.getId() == R.id.actStartDestination) viewModel.setStartDestination(destinationId);
        else viewModel.setEndDestination(destinationId);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        AutoCompleteTextView view = getAutoCompleteTextView(v.getId());
        view.showDropDown();
        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) return;
        AutoCompleteTextView view = getAutoCompleteTextView(v.getId());
        setDestination(view, view.getText().toString());
    }

    public void onItemClick(AdapterView<?> parent, int position, int viewId) {
        AutoCompleteTextView view = getAutoCompleteTextView(viewId);
        String provinceName = String.valueOf(parent.getItemAtPosition(position));
        setDestination(view, provinceName);
    }

    @Override
    public void onClick(View v) {
        // CALL API SEARCH JOURNEY.
    }
}