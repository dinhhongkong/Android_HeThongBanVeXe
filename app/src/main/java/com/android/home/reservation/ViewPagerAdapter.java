package com.android.home.reservation;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.android.home.reservation.journey.JourneyFragment;

import java.util.Map;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private Map<String, Object> data;

    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        boolean isOneWayTicket = position == 0;
        Integer originId = (Integer) (isOneWayTicket ? data.get("originId") : data.get("destinationId"));
        Integer destinationId = (Integer) (isOneWayTicket ? data.get("destinationId") : data.get("originId"));
        String startDate = (String) (isOneWayTicket ? data.get("startDate") : data.get("endDate"));
        return JourneyFragment.newInstance(originId, destinationId, startDate);
    }

    @Override
    public int getItemCount() {
        return data.get("endDate") == null ? 1 : 2;
    }

    public String getPageTitle(int position) {
        return (position == 0 ? "CHUYẾN ĐI (" + data.get("startDate") : "CHUYẾN VỀ (" + data.get("endDate")) + ")";
    }
}
