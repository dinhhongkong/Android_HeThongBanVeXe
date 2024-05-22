package com.android.feature.home.ticket;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.android.R;
import com.android.databinding.FragmentTicketBinding;
import com.android.feature.home.reservation.ReservationViewModel;
import com.android.model.Province;
import com.android.model.request.JourneyRequest;
import com.android.utils.ActionBarUtils;
import com.android.utils.DateUtils;

import java.util.Date;

public class TicketFragment extends Fragment{

    private FragmentTicketBinding binding;
    private ReservationViewModel viewModel;
    private ArrayAdapter<Province> provinceAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ReservationViewModel.class);
        viewModel.loadAllProvince();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ActionBarUtils.setTitle("ĐẶT VÉ XE");
        binding = FragmentTicketBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAdapter();
        setEvent();
        observeData();
    }

    private void initAdapter() {
        viewModel.getProvinces().observe(getViewLifecycleOwner(), provinces -> {
            provinceAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, provinces);
            binding.actOrigin.setAdapter(provinceAdapter);
            binding.actDestination.setAdapter(provinceAdapter);
        });
    }

    private void observeData() {
        viewModel.getIsRoundTrip().observe(getViewLifecycleOwner(), isRoundTrip ->{
            if (isRoundTrip) {
                binding.edtEndDate.setVisibility(View.VISIBLE);
                binding.txtEndDate.setVisibility(View.VISIBLE);
            }
            else {
                binding.edtEndDate.setVisibility(View.INVISIBLE);
                binding.txtEndDate.setVisibility(View.INVISIBLE);
            }

        });

        viewModel.getJourney().observe(getViewLifecycleOwner(), journey ->{
            binding.edtStartDate.setText(journey.getStartDate());
            binding.edtEndDate.setText(journey.getEndDate());
        });
    }


    private void setEvent() {

        // SHOW DROPDOWN LIST EVENT.
        binding.actOrigin.setOnClickListener(v->{
            binding.actOrigin.showDropDown();
        });

        binding.actDestination.setOnClickListener(v->{
            binding.actDestination.showDropDown();
        });
        // TYPE DESTINATION EVENT.
//        binding.actOrigin.setOnFocusChangeListener();
//        binding.actDestination.setOnFocusChangeListener();
        // CHOOSE DESTINATION EVENT.
        binding.actOrigin.setOnItemClickListener((parent, view, position, id) -> {
            viewModel.getJourney().getValue().setOriginProvinceId(position+1);
        });
        binding.actDestination.setOnItemClickListener((parent, view, position, id) -> {
            viewModel.getJourney().getValue().setDestProvinceId(position+1);
        });
        // CLEAR HISTORY EVENT.
        binding.btnClearHistory.setOnClickListener(v->{

        });
        // SUBMIT EVENT.
        binding.btnSubmit.setOnClickListener(v->{
            goToJourneyFragment();
        });


        binding.edtStartDate.setOnClickListener(v->{
            setStartDate();
        });

        binding.edtEndDate.setOnClickListener(v->{
            setEndDate();
        });

        binding.rdbTwoWay.setOnClickListener(v->{
            viewModel.setIsRoundTrip(binding.rdbTwoWay.isChecked());
        });

        binding.rdbOneWay.setOnClickListener(v->{
            viewModel.setIsRoundTrip(binding.rdbTwoWay.isChecked());
        });
    }

    private AutoCompleteTextView getAutoCompleteTextView(int viewId) {
        if (viewId == R.id.actOrigin)
            return binding.actOrigin;
        else return binding.actDestination;
    }

    public void setStartDate() {
        DatePickerDialog dialog = new DatePickerDialog(requireContext());
        dialog.setOnDateSetListener((view1, year, month, dayOfMonth) -> {
            Date date = DateUtils.getDate(year, month, dayOfMonth);
            binding.edtStartDate.setText(DateUtils.format(date));
            viewModel.getJourney().getValue().setStartDate(DateUtils.format(date));

        });
        // CONSTRAINT MIN & MAX DATE.
        DatePicker datePicker = dialog.getDatePicker();
        datePicker.setMinDate(DateUtils.getDate().getTime());
        if (!binding.edtEndDate.getText().toString().isEmpty()) {
            datePicker.setMaxDate(DateUtils.parse(binding.edtEndDate.getText().toString()).getTime());
        }

        dialog.show();
    }


    public void setEndDate() {
        DatePickerDialog dialog = new DatePickerDialog(requireContext());
        dialog.setOnDateSetListener((view1, year, month, dayOfMonth) -> {
            Date date = DateUtils.getDate(year, month, dayOfMonth);
            binding.edtEndDate.setText(DateUtils.format(date));
            viewModel.getJourney().getValue().setEndDate(DateUtils.format(date));
        });
        // CONSTRAINT MIN & MAX DATE.
        DatePicker datePicker = dialog.getDatePicker();
        datePicker.setMinDate(DateUtils.parse(binding.edtStartDate.getText().toString()).getTime());

        dialog.show();
    }



    public boolean validate() {
        if (binding.actOrigin.getText().toString().isEmpty()) {
            Toast.makeText(requireContext(), "Vui lòng chọn điểm đi!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (binding.actDestination.getText().toString().isEmpty()) {
            Toast.makeText(requireContext(), "Vui lòng chọn điểm đến!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (binding.edtStartDate.getText().toString().isEmpty()) {
            Toast.makeText(requireContext(), "Vui lòng chọn ngày đi!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (binding.rdbTwoWay.isChecked() && binding.edtEndDate.toString().isEmpty()) {
            Toast.makeText(requireContext(), "Vui lòng chọn ngày về!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void goToJourneyFragment() {
//        if (!validate()) {
//            return;
//        }

        Navigation.findNavController(requireView()).navigate(R.id.action_ticketFragment_to_reservationFragment);

    }




}