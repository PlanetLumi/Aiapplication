package com.example.aiapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class saveUserID {
    public static void saveID(Context context, Long userID) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("userID", userID);
        editor.apply();
    }
}
