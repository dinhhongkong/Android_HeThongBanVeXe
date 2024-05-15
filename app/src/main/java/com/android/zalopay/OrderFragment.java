package com.android.zalopay;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.R;
import com.android.databinding.FragmentOrderBinding;
import com.android.model.response.JourneyResponse;
import com.android.utils.NumberUtils;
import com.android.zalopay.Api.CreateOrder;
import com.android.zalopay.Constant.AppInfo;

import org.json.JSONObject;

import java.util.List;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class OrderFragment extends Fragment {
    private FragmentOrderBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        // PAYMENT SDK INIT.
        ZaloPaySDK.init(AppInfo.APP_ID, Environment.SANDBOX);
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOrderBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setControl();
        setEvent();
    }

    private void setControl() {
//        binding.Origin.setText(item.getOrigin().getName());
//        binding.Destination.setText(item.getDestination().getName());
//        binding.StartDate.setText(item.getStartTime());
//        binding.Duration.setText(NumberUtils.formatIntegerConstraint(item.getDuration()));
//        binding.Price.setText(NumberUtils.format(item.getPrice()));
//        binding.TicketQuantity.setText(String.valueOf(listChosenSeat.size()));
//        binding.Total.setText(NumberUtils.format(item.getPrice() * listChosenSeat.size()));
    }

    private void setEvent() {
        binding.btnCancel.setOnClickListener(v -> goBack());

        binding.btnOrder.setOnClickListener(v -> {
            CreateOrder orderApi = new CreateOrder();
            try {
                // PAYMENT INFORMATION.
                double totalDouble = NumberUtils.parse(binding.Price.getText().toString());
                if (Double.isNaN(totalDouble)) {
                    Toast.makeText(getContext(), "Đã có lỗi xảy ra. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // PAYMENT EXECUTION.
                String total = String.valueOf((int) totalDouble);
                JSONObject data = orderApi.createOrder(total);
                String code = data.getString("return_code");
                if (code.equals("1")) {
                    String token = data.getString("zp_trans_token");
                    ZaloPaySDK.getInstance().payOrder(OrderFragment.this.requireActivity(), token, "demozpdk://app", new PayOrderListener() {
                        @Override
                        public void onPaymentSucceeded(final String transactionId, final String transToken, final String appTransID) {
                            Toast.makeText(OrderFragment.this.requireActivity(), "Thanh toán thành công!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onPaymentCanceled(String zpTransToken, String appTransID) {
                            Toast.makeText(OrderFragment.this.requireActivity(), "Thanh toán bị hủy!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onPaymentError(ZaloPayError zaloPayError, String zpTransToken, String appTransID) {
                            Toast.makeText(OrderFragment.this.requireActivity(), "Thanh toán thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } catch (Exception ignored) {
            }
        });
    }

    private void goBack() {
        View root = getView();
        if (root == null) return;
//        Navigation.findNavController(root).navigate(R.id.action_orderFragment_to_reservationFragment);
    }
}