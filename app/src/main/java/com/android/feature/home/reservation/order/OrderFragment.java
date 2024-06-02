package com.android.feature.home.reservation.order;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.databinding.FragmentOrderBinding;
import com.android.feature.home.reservation.ReservationViewModel;
import com.android.model.Ticket;
import com.android.utils.DateUtils;
import com.android.utils.NumberUtils;
import com.android.zalopay.Api.CreateOrder;
import com.android.zalopay.Constant.AppInfo;

import org.json.JSONObject;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class OrderFragment extends Fragment {
    private FragmentOrderBinding binding;

    private ReservationViewModel viewModel;

    private double priceDeparture = 0;
    private double priceReturn = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        // PAYMENT SDK INIT.
        ZaloPaySDK.init(AppInfo.APP_ID, Environment.SANDBOX);
        viewModel = new ViewModelProvider(requireActivity()).get(ReservationViewModel.class);
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOrderBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setView();
        setEvent();
    }

    @SuppressLint("SetTextI18n")
    private void setView() {

        Ticket departureTicket = viewModel.getDepartureTicket().getValue();
        binding.tvFullName.setText(departureTicket.getFullName());
        binding.tvPhoneNumber.setText(departureTicket.getPhoneNumber());
        binding.tvEmail.setText(departureTicket.getEmail());

        binding.tvDepartureName.setText(departureTicket.getJourney().getDepartureProvince() + " -> " + departureTicket.getJourney().getDestProvince());
        binding.tvDepartureDate.setText(DateUtils.convertToDDMMYYYY(departureTicket.getJourney().getStartDate()));
        binding.tvDepartureSeatNumber.setText(Integer.toString(departureTicket.getSeatNameList().size()) );
        binding.tvDepartureSeatName.setText(departureTicket.getSeatNameList().toString());
        priceDeparture = departureTicket.getSeatNameList().size() * departureTicket.getJourney().getPrice();
        binding.tvDepartureTotalPrice.setText(NumberUtils.format(priceDeparture)  + "đ");
        binding.tvPriceDeparture.setText(NumberUtils.format(priceDeparture) + "đ");

        if (viewModel.getIsRoundTrip().getValue()){
            binding.cvInfoReturn.setVisibility(View.VISIBLE);
            Ticket returnTicket = viewModel.getReturnTicket().getValue();
            binding.tvReturnName.setText(returnTicket.getJourney().getDepartureProvince() + " -> " + returnTicket.getJourney().getDestProvince());
            binding.tvReturnDate.setText(DateUtils.convertToDDMMYYYY(returnTicket.getJourney().getStartDate()));
            binding.tvReturnSeatNumber.setText( Integer.toString(returnTicket.getSeatNameList().size()) );
            binding.tvReturnSeatName.setText(returnTicket.getSeatNameList().toString());
            priceReturn = returnTicket.getSeatNameList().size() * returnTicket.getJourney().getPrice();
            binding.tvReturnTotalPrice.setText(NumberUtils.format(priceReturn)  + "đ");
            binding.tvPriceReturn.setText(NumberUtils.format(priceReturn) + "đ");
        }

        binding.tvTotalPrice.setText( NumberUtils.format(priceDeparture + priceReturn)  + "đ");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void setEvent() {

        binding.btnOrder.setOnClickListener(v -> {
            CreateOrder orderApi = new CreateOrder();
            try {
                // PAYMENT INFORMATION.
                double totalDouble = priceDeparture + priceReturn;
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
                            paymentSuccess(transactionId);
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

    private void paymentSuccess(String transactionId) {
        if(viewModel.getIsRoundTrip().getValue()) {
            viewModel.orderRoundTripTicket(transactionId);
        }
        else {
            viewModel.orderOneWayTicket(transactionId);
        }
    }

}