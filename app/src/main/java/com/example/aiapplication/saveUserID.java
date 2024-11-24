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
    public void clearID(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("userID");
        editor.apply();
    }
    public static long grabID(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        return sharedPreferences.getLong("userID", -1);
    }
}
