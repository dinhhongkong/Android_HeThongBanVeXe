package com.android.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.auth0.android.jwt.JWT;

public class JwtUtils {
    private static final String TOKEN_KEY = "token";

    // Lưu token vào SharedPreferences
    public static void saveToken(Context context, String token) {
        SharedPreferences sharedPreferences = SharedPref.getSharedPref(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_KEY, token);
        editor.apply();
    }

    // Lấy token từ SharedPreferences
    public static String getToken(Context context) {
        SharedPreferences sharedPreferences = SharedPref.getSharedPref(context);
        return sharedPreferences.getString(TOKEN_KEY, "");
    }

    // Giải mã và trích xuất thông tin từ JWT
    public static void decodeJWT(String token) {
        String userId = "";
        try {
            JWT jwt = new JWT(token);
            // Trích xuất thông tin từ payload
            userId = jwt.getClaim("customerId").asString();

            // In ra các thông tin
            Log.d("JWT", "User ID: " + userId);
        } catch (Exception e) {
            Log.e("JWT", "Invalid token", e);
        }
    }


    public static int getUserId(String token) {
        String userId = "";
        try {
            JWT jwt = new JWT(token);
            // Trích xuất thông tin từ payload
            userId = jwt.getClaim("customerId").asString();

            // In ra các thông tin
            Log.d("JWT", "User ID: " + userId);
        } catch (Exception e) {
            Log.e("JWT", "Invalid token", e);
        }
        return Integer.parseInt(userId);
    }
}
