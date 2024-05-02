package com.android;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.android.utils.ActionBarUtils;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBarUtils.setActivity(this);
    }
}