package com.android.home.reservation;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.MainActivity;
import com.android.R;
import com.android.databinding.FragmentReservationBinding;
import com.android.home.reservation.seat.AdapterSeat;
import com.android.model.response.JourneyRespone;
import com.android.utils.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ReservationFragment extends Fragment implements AdapterJourney.OnItemListener {
    private final List<String> listChosenSeat = new ArrayList<>();
    private AdapterJourney adapterJourney;
    private FragmentReservationBinding mFragmentReservationBinding;

    @Override
    public void onDestroy() {
        MainActivity.toggleActionBar(getActivity(), true);
        super.onDestroy();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MainActivity.toggleActionBar(getActivity(), false);

        this.mFragmentReservationBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_reservation, container, false);
        ReservationViewModel reservationViewModel = new ReservationViewModel();
        mFragmentReservationBinding.setReservationViewModel(reservationViewModel);

        return mFragmentReservationBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // JOURNEY.
        this.adapterJourney = new AdapterJourney(this);
        mFragmentReservationBinding.rvJourney.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mFragmentReservationBinding.rvJourney.addItemDecoration(new SpaceItemDecoration(50));
        mFragmentReservationBinding.rvJourney.setAdapter(adapterJourney);
        // CALL API SEARCH JOURNEY.
    }

    @Override
    public void onItemClick(View view, int position) {
        JourneyRespone journey = adapterJourney.getItem(position);
        if (journey.getAvailableSeat() == 0) return;

        view.setBackgroundResource(R.drawable.bg_selected);
        Dialog dialog = getDialog(view);

        Window window = dialog.getWindow();
        if (window == null) return;

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.BOTTOM;
        window.setAttributes(windowAttributes);

        RecyclerView belowSeats = dialog.findViewById(R.id.BelowSeats);
        RecyclerView aboveSeats = dialog.findViewById(R.id.AboveSeats);
        Button btnConfirm = dialog.findViewById(R.id.btnConfirm);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        loadRecyclerView(view.getContext(), belowSeats, "A", journey.getListReservedSeat());
        loadRecyclerView(view.getContext(), aboveSeats, "B", journey.getListReservedSeat());
        btnConfirm.setOnClickListener(v -> {
            // CALL API RESERVE TICKET.
            dialog.dismiss();
        });
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    @SuppressLint("DefaultLocale")
    private void loadRecyclerView(Context context, RecyclerView rvDisplay, String typeCode, List<String> listReservedSeat) {
        // ADAPTER.
        List<String> data = IntStream.rangeClosed(1, 22).boxed()
                .map(code -> String.format("%s%02d", typeCode, code))
                .collect(Collectors.toList());
        AdapterSeat adapter = new AdapterSeat(data, listChosenSeat, listReservedSeat);
        // RECYCLER VIEW.
        rvDisplay.addItemDecoration(new SpaceItemDecoration(50, 3));
        rvDisplay.setLayoutManager(new GridLayoutManager(context, 4));
        rvDisplay.setAdapter(adapter);
    }

    @NonNull
    private Dialog getDialog(View view) {
        Dialog dialogForm = new Dialog(requireContext());
        dialogForm.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogForm.setContentView(R.layout.dialog_choose_seat);
        dialogForm.setCancelable(true);
        dialogForm.setOnDismissListener(dialog -> view.setBackgroundResource(R.drawable.bgi_journey));
        return dialogForm;
    }
}