package com.android.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class DateUtils {
    private static final SimpleDateFormat formatterDate = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat formatterDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static Date getDate() {
        return Calendar.getInstance().getTime();
    }

    public static Date getDate(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);

        return calendar.getTime();
    }

    public static String getCalculatedDateString(String datePart, String timePart, double duration) {
        // SPLIT TIME INTO HOUR & MINUTE PARTS.
        String[] timeParts = timePart.split(":");
        // COMBINE DATE & TIME PARTS.
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parse(datePart));
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeParts[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeParts[1]));
        calendar.add(Calendar.HOUR_OF_DAY, (int) duration);

        return formatterDateTime.format(calendar.getTime());
    }

    public static Date parse(String date) {
        Calendar calendar = Calendar.getInstance();
        try {
            return formatterDate.parse(date);
        } catch (Exception ignored) {
        }
        return calendar.getTime();
    }

    public static String format(Date date) {
        return formatterDate.format(date);
    }
}
