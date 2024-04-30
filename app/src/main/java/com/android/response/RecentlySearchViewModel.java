package com.android.response;

import com.android.model.Province;

import java.util.Objects;

public class RecentlySearchViewModel {
    private String departureTime;
    private Province origin, destination;

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public Province getOrigin() {
        return origin;
    }

    public void setOrigin(Province origin) {
        this.origin = origin;
    }

    public Province getDestination() {
        return destination;
    }

    public void setDestination(Province destination) {
        this.destination = destination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecentlySearchViewModel that = (RecentlySearchViewModel) o;
        return Objects.equals(departureTime, that.departureTime) && Objects.equals(origin, that.origin) && Objects.equals(destination, that.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departureTime, origin, destination);
    }
}
