package com.android.feature.home.reservation;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.android.feature.home.reservation.journey.DepartureFragment;
import com.android.feature.home.reservation.journey.ReturnFragment;

public class ReservationViewPagerAdapter extends FragmentStateAdapter {
    private final boolean isRoundTrip;
    public ReservationViewPagerAdapter(@NonNull Fragment fragment, boolean isRoundTrip) {
        super(fragment);
        this.isRoundTrip = isRoundTrip;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return DepartureFragment.newInstance();
        }
        return ReturnFragment.newInstance();
    }

    @Override
    public int getItemCount() {
        return isRoundTrip ? 2 : 1;
    }

}
