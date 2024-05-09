package com.android.home.ticket;

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
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.R;
import com.android.databinding.FragmentTicketBinding;
import com.android.home.ticket.recently_search.RecentlySearchAdapter;
import com.android.model.Province;
import com.android.model.response.RecentlySearchResponse;
import com.android.utils.ActionBarUtils;
import com.android.utils.SpaceItemDecoration;

public class TicketFragment extends Fragment implements View.OnTouchListener, View.OnFocusChangeListener, View.OnClickListener,
        RecentlySearchAdapter.OnItemListener {
//    private static final List<Province> PROVINCES;
//
//    static {
//        PROVINCES = new ArrayList<>();
//        ServiceProvince.listProvince(PROVINCES);
//    }

    private ArrayAdapter<Province> adapterProvince;
    private RecentlySearchAdapter adapterRecentlySearch;
    private FragmentTicketBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ActionBarUtils.setTitle("ĐẶT VÉ XE");
        binding = FragmentTicketBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setData();
        setEvent();
    }



    private void setData() {
        // PROVINCE ADAPTER.
        adapterProvince = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, PROVINCES);
        // RECENTLY SEARCH.
        this.adapterRecentlySearch = new RecentlySearchAdapter(this);
        binding.rvRecentlySearch.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.rvRecentlySearch.addItemDecoration(new SpaceItemDecoration(50));
        binding.rvRecentlySearch.setAdapter(adapterRecentlySearch);
    }


    private void setEvent() {
        AutoCompleteTextView actOrigin = binding.actOrigin;
        AutoCompleteTextView actDestination = binding.actDestination;
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
        if (viewId == R.id.actOrigin) return binding.actOrigin;
        else return binding.actDestination;
    }

    private void setDestination(AutoCompleteTextView view, String name) {
        TicketViewModel viewModel = mFragmentTicketBinding.getTicketViewModel();
        Province province = PROVINCES.stream().filter(p -> p.getName().equals(name)).findFirst().orElse(null);
        if (province == null) view.setText("");
        else {
            if (view.getId() == R.id.actOrigin)
                viewModel.setOrigin(province);
            else viewModel.setDestination(province);
        }
    }


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
            if (!mFragmentTicketBinding.getTicketViewModel().validate(v.getContext())) return;
            storeRecentlySearch();
            // REDIRECT TO RESERVATION PAGE.
            View view = getView();
            if (view == null) return;

            Bundle data = new Bundle();
            TicketViewModel viewModel = mFragmentTicketBinding.getTicketViewModel();
            data.putSerializable("origin", viewModel.getOrigin());
            data.putSerializable("destination", viewModel.getDestination());
            data.putString("startDate", viewModel.getStartDate());
            data.putString("endDate", viewModel.getEndDate());

            Navigation.findNavController(view).navigate(R.id.action_ticketFragment_to_reservationFragment, data);
        }
    }

    private void storeRecentlySearch() {
        TicketViewModel viewModel = mFragmentTicketBinding.getTicketViewModel();
        RecentlySearchResponse item = new RecentlySearchResponse();
        item.setOrigin(viewModel.getOrigin());
        item.setDestination(viewModel.getDestination());
        item.setDepartureDate(viewModel.getStartDate());
        adapterRecentlySearch.addData(item);
    }

    @Override
    public void onItemClickListener(View v, int position) {
        RecentlySearchResponse item = adapterRecentlySearch.getItem(position);
        binding.actOrigin.setText(item.getOrigin().getName());
        binding.actDestination.setText(item.getDestination().getName());
        binding.edtStartDate.setText(item.getDepartureDate());
    }
}