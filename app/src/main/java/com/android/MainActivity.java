package com.android;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static void setActionBarTitle(Activity activity, String title) {
        MainActivity mainActivity = (MainActivity) activity;
        if (mainActivity != null) {
            ActionBar actionBar = mainActivity.getSupportActionBar();
            if (actionBar != null) actionBar.setTitle(title);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}