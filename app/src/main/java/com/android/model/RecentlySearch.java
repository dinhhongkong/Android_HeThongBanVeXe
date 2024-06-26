package com.android.model;

import java.util.Objects;

public class RecentlySearch {
    private String departureDate;
    private Province origin, destination;

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
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
        RecentlySearch that = (RecentlySearch) o;
        return Objects.equals(departureDate, that.departureDate) && Objects.equals(origin, that.origin) && Objects.equals(destination, that.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departureDate, origin, destination);
    }
}
