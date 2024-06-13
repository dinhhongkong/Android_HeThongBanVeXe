package com.android.feature.register;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.databinding.FragmentRegisterBinding;
import com.android.model.request.RegisterRequest;


public class RegisterFragment extends Fragment {
    private FragmentRegisterBinding binding;
    private RegisterViewModel registerViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        registerViewModel.getMessage().observe(getViewLifecycleOwner() ,message -> {
//            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
//        });

        binding.btnRegister.setOnClickListener(v->{
            if (validateForm()) {
                String phoneNumber = binding.edtPhoneNumber.getText().toString();
                String password = binding.edtPassword.getText().toString();
                registerViewModel.setRegisterRequest(new RegisterRequest(phoneNumber,password));
                registerViewModel.registerNewUser();
                Toast.makeText(requireContext(), "Đăng kí thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private boolean validateForm() {
        boolean isValid = true;

        String phoneNumber = binding.edtPhoneNumber.getText().toString();
        String password = binding.edtPassword.getText().toString();
        String confirmPassword = binding.edtConfirmPassword.getText().toString();

        if (phoneNumber.isEmpty() || !Patterns.PHONE.matcher(phoneNumber).matches()) {
            binding.edtPhoneNumber.setError("Số điện thoại không hợp lệ");
            isValid = false;
        }

        if (password.isEmpty() || password.length() < 6) {
            binding.edtPassword.setError("Mật khẩu phải có ít nhất 6 ký tự");
            isValid = false;
        }

        if (!confirmPassword.equals(password)) {
            binding.edtConfirmPassword.setError("Mật khẩu xác nhận không khớp");
            isValid = false;
        }

        if (!binding.ckbPolicy.isChecked()) {
            // Hiển thị thông báo lỗi hoặc xử lý trường hợp người dùng chưa đồng ý với điều khoản
            Toast.makeText(requireContext(), "Bạn chưa đồng ý với điều khoản", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        return isValid;
    }


}