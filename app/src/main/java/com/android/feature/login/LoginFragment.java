package com.android.feature.login;

import static androidx.navigation.Navigation.findNavController;

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
import com.android.core.ViewModelFactory;
import com.android.databinding.FragmentLoginBinding;
import com.android.model.request.LoginRequest;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    private LoginViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelFactory factory = new ViewModelFactory(requireActivity().getApplication());
        viewModel = new ViewModelProvider(this, factory).get(LoginViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setObserver();
        setControl();
        viewModel.validateToken();

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setControl() {
        binding.btnLogin.setOnClickListener(v->{
            loginEvent();
        });
        binding.tvDky.setOnClickListener(v->{
            findNavController(requireView()).navigate(R.id.action_loginFragment_to_registerFragment);
        });
    }

    private void setObserver() {
        viewModel.getLoginStatus().observe(getViewLifecycleOwner(),status->{
            if (status == LoginStatus.SUCCESS) {
                findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment);
                Toast.makeText(requireContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                viewModel.setLoginStatus(LoginStatus.NONE);
            }
            else if (status == LoginStatus.AUTHORIZED) {
                Toast.makeText(requireContext(), "Đang đăng nhập", Toast.LENGTH_SHORT).show();
            }
            else if (status == LoginStatus.ERROR) {
                Toast.makeText(requireContext(), "Thông tin tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
            }
            else if(status == LoginStatus.UNAUTHORIZED) {
                Toast.makeText(requireContext(), "Thời gian đăng nhập hết hạn", Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getLoginRequest().observe(getViewLifecycleOwner(),loginRequest->{
            binding.edtUsername.setText(loginRequest.getUsername());
            binding.edtPassword.setText(loginRequest.getPassword());
        });
    }

    private void loginEvent() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(binding.edtUsername.getText().toString());
        loginRequest.setPassword(binding.edtPassword.getText().toString());
        viewModel.setLoginRequest(loginRequest);
        viewModel.userLogin();
    }


}