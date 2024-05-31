package com.android.feature.home.reservation.customerinfo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.android.R;
import com.android.databinding.FragmentCustomerInfoBinding;
import com.android.feature.home.reservation.ReservationViewModel;
import com.android.model.Ticket;


public class CustomerInfoFragment extends Fragment {
    private FragmentCustomerInfoBinding binding;
    private ReservationViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ReservationViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCustomerInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.edtFullName.setOnTouchListener((view1, motionEvent) -> {
            binding.outlinedTextFieldFullname.setError(null);
            return false;
        });

        binding.edtPhoneNumber.setOnTouchListener((view1, motionEvent)->{
            binding.outlinedTextFieldPhoneNumber.setError(null);
            return false;
        });

        binding.edtEmail.setOnTouchListener((view1, motionEvent)->{
            binding.outlinedTextFieldEmail.setError(null);
            return false;
        });

        binding.btnSubmit.setOnClickListener(v->{
            if (validateForm()) {
                createTicket();
                Navigation.findNavController(requireView()).navigate(R.id.action_customerInfoFragment_to_orderFragment);
            }
            else {
                Toast.makeText(requireContext(), "Kiểm tra lại thông tin nhập vào", Toast.LENGTH_SHORT).show();
            }
        });
    }



    public boolean validateForm() {
        boolean isValid = true;

        // Kiểm tra họ tên
        String fullName = binding.edtFullName.getText().toString();
        if (fullName.isEmpty() || fullName.length() > 20) {
            binding.outlinedTextFieldFullname.setError("Tên không hợp lệ");
            isValid = false;
        } else {
            binding.outlinedTextFieldFullname.setError(null);
        }

        // Kiểm tra số điện thoại
        String phoneNumber = binding.edtPhoneNumber.getText().toString();
        if (phoneNumber.isEmpty() || !phoneNumber.matches("\\d{10}")) {
            binding.outlinedTextFieldPhoneNumber.setError("Số điện thoại không hợp lệ");
            isValid = false;
        } else {
            binding.outlinedTextFieldPhoneNumber.setError(null);
        }

        // Kiểm tra email
        String email = binding.edtEmail.getText().toString();
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.outlinedTextFieldEmail.setError("Email không hợp lệ");
            isValid = false;
        } else {
            binding.outlinedTextFieldEmail.setError(null);
        }

        return isValid;
    }

    private void createTicket() {
        if(viewModel.getIsRoundTrip().getValue()) {
            Ticket departureTicket = viewModel.getDepartureTicket().getValue();
            departureTicket.setFullName(binding.edtFullName.getText().toString());
            departureTicket.setPhoneNumber(binding.edtPhoneNumber.getText().toString());
            departureTicket.setEmail(binding.edtEmail.getText().toString());
            viewModel.setDepartureTicket(departureTicket);

            Ticket returnTicket = viewModel.getReturnTicket().getValue();
            returnTicket.setFullName(binding.edtFullName.getText().toString());
            returnTicket.setPhoneNumber(binding.edtPhoneNumber.getText().toString());
            returnTicket.setEmail(binding.edtEmail.getText().toString());
            viewModel.setReturnTicket(returnTicket);
        }
        else {
            Ticket departureTicket = viewModel.getDepartureTicket().getValue();
            departureTicket.setFullName(binding.edtFullName.getText().toString());
            departureTicket.setPhoneNumber(binding.edtPhoneNumber.getText().toString());
            departureTicket.setEmail(binding.edtEmail.getText().toString());
            viewModel.setDepartureTicket(departureTicket);

        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}