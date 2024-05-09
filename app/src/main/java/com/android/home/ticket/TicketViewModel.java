package com.android.home.ticket;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.ViewModel;

import com.android.BR;
import com.android.R;
import com.android.model.Province;
import com.android.utils.DateUtils;

import java.util.Date;

public class TicketViewModel extends ViewModel {
    private Integer ticketType;
    private Province origin, destination;
    private String startDate, endDate;

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

    public boolean validate(Context context) {
        if (origin == null) {
            Toast.makeText(context, "Vui lòng chọn điểm đi!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (destination == null) {
            Toast.makeText(context, "Vui lòng chọn điểm đến!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (startDate == null) {
            Toast.makeText(context, "Vui lòng chọn ngày đi!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (ticketType == 1 && endDate == null) {
            Toast.makeText(context, "Vui lòng chọn ngày về!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
