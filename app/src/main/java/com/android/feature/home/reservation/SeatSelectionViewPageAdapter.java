package com.android.feature.home.reservation;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.android.feature.home.reservation.journey.DepartureFragment;
import com.android.feature.home.reservation.journey.ReturnFragment;
import com.android.feature.home.reservation.seat.DepartureSeatFragment;
import com.android.feature.home.reservation.seat.ReturnSeatFragment;

public class SeatSelectionViewPageAdapter extends FragmentStateAdapter {
    private final boolean isRoundTrip;

    public SeatSelectionViewPageAdapter(@NonNull Fragment fragment, boolean isRoundTrip) {
        super(fragment);
        this.isRoundTrip = isRoundTrip;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return DepartureSeatFragment.newInstance();
        }
        return ReturnSeatFragment.newInstance();
    }

    @Override
    public int getItemCount() {
        return isRoundTrip ? 2 : 1;
    }
}
