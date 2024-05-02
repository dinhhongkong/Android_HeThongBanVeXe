package com.android.utils;

import androidx.appcompat.app.ActionBar;

import com.android.MainActivity;

public class ActionBarUtils {
    private static MainActivity activity;

    public static MainActivity getActivity() {
        return activity;
    }

    public static void setActivity(MainActivity activity) {
        ActionBarUtils.activity = activity;
    }

    public static void setTitle(String title) {
        if (activity == null) return;
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar == null) return;
        actionBar.setTitle(title);
    }

    public static void toggle(boolean state) {
        if (activity == null) return;
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar == null) return;
        if (!state) actionBar.hide();
        else actionBar.show();
    }
}
