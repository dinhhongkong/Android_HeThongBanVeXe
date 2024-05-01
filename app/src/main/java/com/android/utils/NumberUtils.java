package com.android.utils;

import java.text.DecimalFormat;

public class NumberUtils {
    private static final DecimalFormat formatter = new DecimalFormat("#,###");

    public static String format(double number) {
        return formatter.format(number);
    }
}
