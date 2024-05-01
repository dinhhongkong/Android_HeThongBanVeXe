package com.android.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    public static Date getDate() {
        return Calendar.getInstance().getTime();
    }

    public static Date getDate(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);

        return calendar.getTime();
    }

    public static Date parse(String date) {
        Calendar calendar = Calendar.getInstance();
        try {
            return formatter.parse(date);
        } catch (Exception ignored) {
        }
        return calendar.getTime();
    }

    public static String format(Date date) {
        return formatter.format(date);
    }
}
