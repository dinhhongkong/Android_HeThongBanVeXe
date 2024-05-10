package com.android.home.ticket;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.databinding.FragmentTicketBinding;
import com.android.utils.ActionBarUtils;

public class TicketFragment extends Fragment{

//    private RecentlySearchAdapter adapterRecentlySearch;
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
//        setData();
//        setEvent();
    }

    public boolean validate() {
//        if (origin == null) {
//            Toast.makeText(context, "Vui lòng chọn điểm đi!", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        if (destination == null) {
//            Toast.makeText(context, "Vui lòng chọn điểm đến!", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        if (startDate == null) {
//            Toast.makeText(context, "Vui lòng chọn ngày đi!", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        if (ticketType == 1 && endDate == null) {
//            Toast.makeText(context, "Vui lòng chọn ngày về!", Toast.LENGTH_SHORT).show();
//            return false;
//        }
        return true;
    }



//    private void setData() {
//        // PROVINCE ADAPTER.
//        adapterProvince = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, PROVINCES);
//        // RECENTLY SEARCH.
//        this.adapterRecentlySearch = new RecentlySearchAdapter(this);
//        binding.rvRecentlySearch.setLayoutManager(
//                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
//        binding.rvRecentlySearch.addItemDecoration(new SpaceItemDecoration(50));
//        binding.rvRecentlySearch.setAdapter(adapterRecentlySearch);
//    }

//
//    private void setEvent() {
//
//        // SHOW DROPDOWN LIST EVENT.
//        binding.actOrigin.setOnTouchListener(this);
//        binding.actDestination.setOnTouchListener(this);
//        // TYPE DESTINATION EVENT.
//        binding.actOrigin.setOnFocusChangeListener(this);
//        binding.actDestination.setOnFocusChangeListener(this);
//        // CHOOSE DESTINATION EVENT.
//        binding.actOrigin.setOnItemClickListener((parent, view, position, id) ->
//                onItemClick(parent, position, R.id.actOrigin));
//        binding.actDestination.setOnItemClickListener((parent, view, position, id) ->
//                onItemClick(parent, position, R.id.actDestination));
//        // CLEAR HISTORY EVENT.
//        binding.btnClearHistory.setOnClickListener(this);
//        // SUBMIT EVENT.
//        binding.btnSubmit.setOnClickListener(this);
//    }
//
//    private AutoCompleteTextView getAutoCompleteTextView(int viewId) {
//        if (viewId == R.id.actOrigin) return binding.actOrigin;
//        else return binding.actDestination;
//    }
//
//    private void setDestination(AutoCompleteTextView view, String name) {
//        TicketViewModel viewModel = mFragmentTicketBinding.getTicketViewModel();
//        Province province = PROVINCES.stream().filter(p -> p.getName().equals(name)).findFirst().orElse(null);
//        if (province == null) view.setText("");
//        else {
//            if (view.getId() == R.id.actOrigin)
//                viewModel.setOrigin(province);
//            else viewModel.setDestination(province);
//        }
//    }
//
//
//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        AutoCompleteTextView view = getAutoCompleteTextView(v.getId());
//        view.showDropDown();
//        return false;
//    }
//
//    @Override
//    public void onFocusChange(View v, boolean hasFocus) {
//        if (hasFocus) return;
//        AutoCompleteTextView view = getAutoCompleteTextView(v.getId());
//        setDestination(view, view.getText().toString());
//    }
//
//    public void onItemClick(AdapterView<?> parent, int position, int viewId) {
//        AutoCompleteTextView view = getAutoCompleteTextView(viewId);
//        setDestination(view, String.valueOf(parent.getItemAtPosition(position)));
//    }
//
//    @Override
//    public void onClick(View v) {
//        if (v.getId() == R.id.btnClearHistory) adapterRecentlySearch.clearData();
//        else {
//            if (!mFragmentTicketBinding.getTicketViewModel().validate(v.getContext())) return;
//            storeRecentlySearch();
//            // REDIRECT TO RESERVATION PAGE.
//            View view = getView();
//            if (view == null) return;
//
//            Bundle data = new Bundle();
//            TicketViewModel viewModel = mFragmentTicketBinding.getTicketViewModel();
//            data.putSerializable("origin", viewModel.getOrigin());
//            data.putSerializable("destination", viewModel.getDestination());
//            data.putString("startDate", viewModel.getStartDate());
//            data.putString("endDate", viewModel.getEndDate());
//
//            Navigation.findNavController(view).navigate(R.id.action_ticketFragment_to_reservationFragment, data);
//        }
//    }

//    private void storeRecentlySearch() {
//        TicketViewModel viewModel = mFragmentTicketBinding.getTicketViewModel();
//        RecentlySearch item = new RecentlySearch();
//        item.setOrigin(viewModel.getOrigin());
//        item.setDestination(viewModel.getDestination());
//        item.setDepartureDate(viewModel.getStartDate());
//        adapterRecentlySearch.addData(item);
//    }

//    @Override
//    public void onItemClickListener(View v, int position) {
//        RecentlySearch item = adapterRecentlySearch.getItem(position);
//        binding.actOrigin.setText(item.getOrigin().getName());
//        binding.actDestination.setText(item.getDestination().getName());
//        binding.edtStartDate.setText(item.getDepartureDate());
//    }
}