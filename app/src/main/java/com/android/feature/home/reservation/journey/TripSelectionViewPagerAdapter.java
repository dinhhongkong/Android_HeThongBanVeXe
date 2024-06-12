package com.android.feature.home.reservation.journey;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.android.feature.home.reservation.journey.page.DepartureFragment;
import com.android.feature.home.reservation.journey.page.ReturnFragment;

public class TripSelectionViewPagerAdapter extends FragmentStateAdapter {
    private final boolean isRoundTrip;
    public TripSelectionViewPagerAdapter(@NonNull Fragment fragment, boolean isRoundTrip) {
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
