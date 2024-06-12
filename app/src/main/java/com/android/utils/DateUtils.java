package com.android.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
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

    public static String convertToYYYYMMDD(String date) {
        SimpleDateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateObj;
        String formattedDate = "";
        try {
            dateObj = originalFormat.parse(date);
            formattedDate = targetFormat.format(dateObj);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    public static String convertToDDMMYYYY(String dateString) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = inputFormat.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return outputFormat.format(date);
    }
}
