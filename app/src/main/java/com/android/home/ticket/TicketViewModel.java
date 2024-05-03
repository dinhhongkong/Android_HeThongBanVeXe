package com.android.home.ticket;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.android.BR;
import com.android.R;
import com.android.model.Province;
import com.android.service.api_implement.ServiceProvince;
import com.android.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TicketViewModel extends BaseObservable {
    private final List<Province> PROVINCES = new ArrayList<>();
    private Integer ticketType;
    private Province origin, destination;
    private String startDate, endDate;

    {
        ServiceProvince.listProvince(PROVINCES);
    }

    public TicketViewModel() {
        this.ticketType = 0;
    }

    public TicketViewModel(Integer ticketType, Province origin, Province destination, String startDate, String endDate) {
        this.ticketType = ticketType;
        this.origin = origin;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public List<Province> getProvinces() {
        return PROVINCES;
    }

    public Province getProvince(String name) {
        return PROVINCES.stream().filter(p -> p.getName().equals(name)).findFirst().orElse(null);
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
    public Province getOrigin() {
        return origin;
    }

    public void setOrigin(Province origin) {
        this.origin = origin;
        notifyPropertyChanged(BR.origin);
    }

    @Bindable
    public Province getDestination() {
        return destination;
    }

    public void setDestination(Province destination) {
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

    public boolean validate() {
        return origin != null && destination != null;
    }
}
