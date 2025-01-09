package com.example.aiapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class saveUserID {
    //Saves ID to shared preferences
    public static void saveID(Context context, Long userID) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("userID", userID);
        editor.apply();
    }
    //Saves API keys to shared preference
    public static void saveKeys(Context context, String key, String type){
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(type, key);
        editor.apply();
    }
    //Saves user name to shared preferences

    public static void saveName(Context context, String userName){
    SharedPreferences sharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userName",userName);
        editor.apply();
    }

    //Grabs ID from shared preferences and returns as long value defaults to -1
    public static long grabID(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        return sharedPreferences.getLong("userID", -1);
    }
    //Grabs API key from shared preferences and returns as string defaults to empty string
    public static String grabKey(Context context, String keyName){
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        return sharedPreferences.getString(keyName, "");
    }

    //Grabs user name from shared preferences and returns as string defaults to null
    public static String grabName(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        return sharedPreferences.getString("userName", null);
    }
    //Grabs the settings file name of the user
    public static String grabSettings(Context context) {
        return "settings_" + grabName(context) + ".txt";
    }

    //Clears all the data from shared preferences when log out
    public static void clearID(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
