package com.android.home.ticket;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.android.BR;
import com.android.R;

public class TicketViewModel extends BaseObservable {
    private int ticketType;
    private String source, destination;
    private String startDate, endDate;

    public TicketViewModel() {

    }

    public TicketViewModel(int ticketType, String source, String destination, String startDate, String endDate) {
        this.ticketType = ticketType;
        this.source = source;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Bindable
    public int getTicketType() {
        return ticketType;
    }

    public void setTicketType(int ticketType) {
        this.ticketType = ticketType;
        notifyPropertyChanged(BR.ticketType);
    }

    @Bindable
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
        notifyPropertyChanged(BR.source);
    }

    @Bindable
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
        notifyPropertyChanged(BR.destination);
    }

    @Bindable
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
        notifyPropertyChanged(BR.startDate);
    }

    @SuppressLint("DefaultLocale")
    public void setStartDate(View view) {
        DatePickerDialog dialog = new DatePickerDialog(view.getContext());
        dialog.setOnDateSetListener((view1, year, month, dayOfMonth) ->
                setStartDate(String.format("%02d/%02d/%d", dayOfMonth, month, year)));
        dialog.show();
    }

    @Bindable
    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
        notifyPropertyChanged(BR.endDate);
    }

    @SuppressLint("DefaultLocale")
    public void setEndDate(View view) {
        DatePickerDialog dialog = new DatePickerDialog(view.getContext());
        dialog.setOnDateSetListener((view1, year, month, dayOfMonth) ->
                setEndDate(String.format("%02d/%02d/%d", dayOfMonth, month, year)));
        dialog.show();
    }

    public void setTicketTypeChoice(View view, boolean isChecked) {
        int viewId = view.getId();
        if (viewId == R.id.rdbOneWay && isChecked) {
            setTicketType(0);
        } else if (viewId == R.id.rdbTwoWay && isChecked) {
            setTicketType(1);
        }
    }

    public void searchJourney(View view) {
        // CALL API SEARCH JOURNEY & DISPLAY DATA.
    }
}
