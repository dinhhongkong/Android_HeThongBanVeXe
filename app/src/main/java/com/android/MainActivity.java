package com.android;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static void setActionBarTitle(Activity activity, String title) {
        MainActivity mainActivity = (MainActivity) activity;
        if (mainActivity == null) return;
        ActionBar actionBar = mainActivity.getSupportActionBar();
        if (actionBar == null) return;
        actionBar.setTitle(title);
    }

    public static void toggleActionBar(Activity activity, boolean state) {
        MainActivity mainActivity = (MainActivity) activity;
        if (mainActivity == null) return;
        ActionBar actionBar = mainActivity.getSupportActionBar();
        if (actionBar == null) return;
        if (!state) actionBar.hide();
        else actionBar.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}