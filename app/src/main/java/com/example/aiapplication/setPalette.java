package com.example.aiapplication;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;

public class setPalette {
    public static int setLayout(Context context, String layoutId) {
        String settings = saveUserID.grabSettings(context);
        if(settings.equals("settings_.txt")){
            return context.getResources().getIdentifier(layoutId, "layout", context.getPackageName());
        }
        if(!new File(context.getFilesDir(),settings).exists()) {
            try {
                initialiseSettings.initialise(context);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        String[] settingsArray = (ReadData.returnData(context, settings).split(","));
        Log.d("settings", settings);
        settingsArray[1] = settingsArray[1].trim().replace("]", "");
        if(settingsArray[1].equals("Sunny")) {
            return context.getResources().getIdentifier(layoutId+"_sunny", "layout", context.getPackageName());

        }
        else if(settingsArray[1].equals("Moody")) {
            return context.getResources().getIdentifier(layoutId+"_moody", "layout", context.getPackageName());
        }
        else {
            return context.getResources().getIdentifier(layoutId, "layout", context.getPackageName());
        }
    }

        public static String findPopUp(Context context){
        String settings = saveUserID.grabSettings(context);
        if(settings.equals("settings_.txt")){
            return "";
        }
        if(!new File(context.getFilesDir(),settings).exists()) {
            try {
                initialiseSettings.initialise(context);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        String[] settingsArray = (ReadData.returnData(context, settings).split(","));
        if(settingsArray[1].equals("Sunny")) {
            return "sunny";
        }
        else if(settingsArray[1].equals("Moody")) {
            return "moody";
        }
        else {
            return "";
        }
    }
}
