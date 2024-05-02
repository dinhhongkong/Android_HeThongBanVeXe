package com.android.utils;

import java.text.DecimalFormat;
import java.util.Objects;

public class NumberUtils {
    private static final DecimalFormat formatter = new DecimalFormat();

    public static String format(double number) {
        formatter.applyPattern("#,###");
        return formatter.format(number);
    }

    public static double parse(String number) {
        formatter.applyPattern("#,###");
        try {
            return Objects.requireNonNull(formatter.parse(number)).doubleValue();
        } catch (Exception ignored) {
        }
        return Double.NaN;
    }

    public static String formatInteger(double number) {
        formatter.applyPattern("#.#");
        return formatter.format(number);
    }
}
