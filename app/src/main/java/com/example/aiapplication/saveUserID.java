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

    public static void saveName(Context context, String userName){
    SharedPreferences sharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userName",userName);
        editor.apply();
    }

    public static long grabID(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        return sharedPreferences.getLong("userID", -1);
    }

    public static String grabName(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        return sharedPreferences.getString("userName", null);
    }
    public static String grabSettings(Context context) {
        return "settings_" + grabName(context) + ".txt";
    }
}
