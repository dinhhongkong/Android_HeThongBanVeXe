package com.android.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    private static SharedPreferences sharedPreferences;
    private SharedPref()
    {

    }

    public static synchronized SharedPreferences getSharedPref(Context context)
    {
        if(sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
            return sharedPreferences;
        }
        return sharedPreferences;
    }


}
