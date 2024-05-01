package com.android.model.response;

import com.android.model.Province;
import com.android.utils.NumberUtils;

import java.util.List;

public class JourneyRespone {
    private static final int TOTAL_SEAT = 44;
    private String startTime, endTime;
    private Province origin, destination;
    private double duration, price;
    private List<String> listReservedSeat;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<String> getListReservedSeat() {
        return listReservedSeat;
    }

    public void setListReservedSeat(List<String> listReservedSeat) {
        this.listReservedSeat = listReservedSeat;
    }

    public int getAvailableSeat() {
        return TOTAL_SEAT - listReservedSeat.size();
    }

    public String toString(int number) {
        return String.valueOf(number);
    }

    public String toString(double number) {
        return NumberUtils.format(number);
    }
}
