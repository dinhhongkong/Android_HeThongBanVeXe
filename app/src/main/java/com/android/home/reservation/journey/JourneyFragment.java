package com.android.home.reservation.journey;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.R;
import com.android.home.reservation.journey.seat.AdapterSeat;
import com.android.model.response.JourneyResponse;
import com.android.service.api_implement.ServiceJourney;
import com.android.utils.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class JourneyFragment extends Fragment implements AdapterJourney.OnItemListener {
    private static final String ARG_PARAM1 = "originId";
    private static final String ARG_PARAM2 = "destinationId";
    private static final String ARG_PARAM3 = "startDate";
    private final List<String> listChosenSeat = new ArrayList<>();
    private int originId, destinationId;
    private String startDate;
    private AdapterJourney adapterJourney;

    public JourneyFragment() {

    }

    public static JourneyFragment newInstance(Integer originId, Integer destinationId, String startDate) {
        JourneyFragment fragment = new JourneyFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, originId);
        args.putInt(ARG_PARAM2, destinationId);
        args.putString(ARG_PARAM3, startDate);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            originId = getArguments().getInt(ARG_PARAM1);
            destinationId = getArguments().getInt(ARG_PARAM2);
            startDate = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_journey, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.adapterJourney = new AdapterJourney(this);
        RecyclerView rvJourney = view.findViewById(R.id.rvJourney);
        rvJourney.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvJourney.addItemDecoration(new SpaceItemDecoration(50));
        rvJourney.setAdapter(adapterJourney);
        // CALL ApiClient SEARCH JOURNEY.
        ServiceJourney.listJourney(originId, destinationId, startDate, adapterJourney);
    }

    @Override
    public void onItemClick(View view, int position) {
        JourneyResponse journey = adapterJourney.getItem(position);
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

        Context context = view.getContext();
        RecyclerView belowSeats = dialog.findViewById(R.id.BelowSeats);
        RecyclerView aboveSeats = dialog.findViewById(R.id.AboveSeats);
        Button btnConfirm = dialog.findViewById(R.id.btnConfirm);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        loadRecyclerView(context, belowSeats, "A", journey.getListReservedSeat());
        loadRecyclerView(context, aboveSeats, "B", journey.getListReservedSeat());
        btnConfirm.setOnClickListener(v -> {
            if (listChosenSeat.isEmpty()) {
                Toast.makeText(context, "Vui lòng chọn ít nhất 1 vé!", Toast.LENGTH_SHORT).show();
                return;
            }

            View root = getView();
            if (root != null) {
                // REDIRECT TO PAYMENT PAGE.
                Bundle data = new Bundle();
                data.putSerializable("journey", journey);
                data.putStringArrayList("chosenSeats", (ArrayList<String>) listChosenSeat);

                Navigation.findNavController(root).navigate(R.id.action_reservationFragment_to_orderFragment, data);
                dialog.dismiss();
            }
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
        dialogForm.setOnDismissListener(dialog -> {
            view.setBackgroundResource(R.drawable.bgi_journey);
            listChosenSeat.clear();
        });
        return dialogForm;
    }
}