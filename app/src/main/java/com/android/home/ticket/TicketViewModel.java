package com.android.home.ticket;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.android.BR;
import com.android.R;
import com.android.utils.DateUtils;

import java.util.Date;

public class TicketViewModel extends BaseObservable {
    private Integer ticketType, startDestination, endDestination;
    private String startDate, endDate;

    public TicketViewModel() {

    }

    public TicketViewModel(Integer ticketType, Integer startDestination, Integer endDestination, String startDate, String endDate) {
        this.ticketType = ticketType;
        this.startDestination = startDestination;
        this.endDestination = endDestination;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Bindable
    public Integer getTicketType() {
        return ticketType;
    }

    public void setTicketType(Integer ticketType) {
        this.ticketType = ticketType;
        notifyPropertyChanged(BR.ticketType);
    }

    @Bindable
    public Integer getStartDestination() {
        return startDestination;
    }

    public void setStartDestination(Integer startDestination) {
        this.startDestination = startDestination;
        notifyPropertyChanged(BR.startDestination);
    }

    @Bindable
    public Integer getEndDestination() {
        return endDestination;
    }

    public void setEndDestination(Integer endDestination) {
        this.endDestination = endDestination;
        notifyPropertyChanged(BR.endDestination);
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
        dialog.setOnDateSetListener((view1, year, month, dayOfMonth) -> {
            Date date = DateUtils.getDate(year, month, dayOfMonth);
            setStartDate(DateUtils.format(date));
        });
        // CONSTRAINT MIN & MAX DATE.
        DatePicker datePicker = dialog.getDatePicker();
        datePicker.setMinDate(DateUtils.getDate().getTime());
        if (endDate != null) {
            datePicker.setMaxDate(DateUtils.parse(endDate).getTime());
        }

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
        dialog.setOnDateSetListener((view1, year, month, dayOfMonth) -> {
            Date date = DateUtils.getDate(year, month, dayOfMonth);
            setEndDate(DateUtils.format(date));
        });
        // CONSTRAINT MIN & MAX DATE.
        DatePicker datePicker = dialog.getDatePicker();
        datePicker.setMinDate(DateUtils.parse(startDate).getTime());

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
}
