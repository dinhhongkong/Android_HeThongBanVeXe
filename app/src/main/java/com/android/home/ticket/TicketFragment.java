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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.MainActivity;
import com.android.R;
import com.android.databinding.FragmentTicketBinding;
import com.android.home.ticket.recently_search.AdapterRecentlySearch;
import com.android.model.Province;
import com.android.response.RecentlySearchViewModel;
import com.android.utils.SpaceItemDecoration;

public class TicketFragment extends Fragment implements View.OnTouchListener, View.OnFocusChangeListener, View.OnClickListener,
        AdapterRecentlySearch.OnItemListener {
    private AdapterRecentlySearch adapterRecentlySearch;
    private FragmentTicketBinding mFragmentTicketBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MainActivity.setActionBarTitle(getActivity(), "ĐẶT VÉ XE");

        this.mFragmentTicketBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_ticket, container, false);
        TicketViewModel ticketViewModel = new TicketViewModel();
        mFragmentTicketBinding.setTicketViewModel(ticketViewModel);

        return mFragmentTicketBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setData();
        setEvent();
    }

    private void setData() {
        // PROVINCE.
        ArrayAdapter<Province> provinceAdapter = new ArrayAdapter<>(
                requireContext(), android.R.layout.simple_dropdown_item_1line);
        mFragmentTicketBinding.actOrigin.setAdapter(provinceAdapter);
        mFragmentTicketBinding.actDestination.setAdapter(provinceAdapter);
        // RECENTLY SEARCH.
        this.adapterRecentlySearch = new AdapterRecentlySearch(this);
        mFragmentTicketBinding.rvRecentlySearch.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mFragmentTicketBinding.rvRecentlySearch.addItemDecoration(new SpaceItemDecoration(50));
        mFragmentTicketBinding.rvRecentlySearch.setAdapter(adapterRecentlySearch);
        // CALL API GET PROVINCE DATA.
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setEvent() {
        AutoCompleteTextView actOrigin = mFragmentTicketBinding.actOrigin;
        AutoCompleteTextView actDestination = mFragmentTicketBinding.actDestination;
        // SHOW DROPDOWN LIST EVENT.
        actOrigin.setOnTouchListener(this);
        actDestination.setOnTouchListener(this);
        // TYPE DESTINATION EVENT.
        actOrigin.setOnFocusChangeListener(this);
        actDestination.setOnFocusChangeListener(this);
        // CHOOSE DESTINATION EVENT.
        actOrigin.setOnItemClickListener((parent, view, position, id) ->
                onItemClick(parent, position, R.id.actOrigin));
        actDestination.setOnItemClickListener((parent, view, position, id) ->
                onItemClick(parent, position, R.id.actDestination));
        // CLEAR HISTORY EVENT.
        mFragmentTicketBinding.btnClearHistory.setOnClickListener(this);
        // SUBMIT EVENT.
        mFragmentTicketBinding.btnSubmit.setOnClickListener(this);
    }

    private AutoCompleteTextView getAutoCompleteTextView(int viewId) {
        if (viewId == R.id.actOrigin) return mFragmentTicketBinding.actOrigin;
        else return mFragmentTicketBinding.actDestination;
    }

    private void setDestination(AutoCompleteTextView view, String name) {
        TicketViewModel viewModel = mFragmentTicketBinding.getTicketViewModel();
        Province province = viewModel.getProvince(name);
        if (province == null) view.setText("");
        else {
            if (view.getId() == R.id.actOrigin) viewModel.setOrigin(province);
            else viewModel.setDestination(province);
        }
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
        setDestination(view, String.valueOf(parent.getItemAtPosition(position)));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnClearHistory) adapterRecentlySearch.clearData();
        else {
            storeRecentlySearch();
            // REDIRECT TO RESERVATION PAGE.
            View view = getView();
            if (view == null) return;
            Navigation.findNavController(view).navigate(R.id.action_ticketFragment_to_reservationFragment);
        }
    }

    private void storeRecentlySearch() {
        TicketViewModel viewModel = mFragmentTicketBinding.getTicketViewModel();
        if (!viewModel.validate()) return;
        RecentlySearchViewModel item = new RecentlySearchViewModel();
        item.setOrigin(viewModel.getOrigin());
        item.setDestination(viewModel.getDestination());
        item.setDepartureDate(viewModel.getStartDate());
        adapterRecentlySearch.addData(item);
    }

    @Override
    public void onItemClickListener(View v, int position) {
        RecentlySearchViewModel item = adapterRecentlySearch.getItem(position);
        mFragmentTicketBinding.actOrigin.setText(item.getOrigin().getName());
        mFragmentTicketBinding.actDestination.setText(item.getDestination().getName());
        mFragmentTicketBinding.edtStartDate.setText(item.getDepartureDate());
    }
}