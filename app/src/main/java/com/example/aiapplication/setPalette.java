package com.example.aiapplication;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;

public class setPalette {
    //Finds the layout of the chosen pallette design by the user
    public static int setLayout(Context context, String layoutId) {
        String settings = saveUserID.grabSettings(context);
        //If settings null set default
        if(settings.equals("settings_.txt")){
            return context.getResources().getIdentifier(layoutId, "layout", context.getPackageName());
        }
        //If file does not exist create default settings
        if(!new File(context.getFilesDir(),settings).exists()) {
            try {
                initialiseSettings.initialise(context);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        //Find user settings
        String[] settingsArray = (ReadData.returnData(context, settings).split(","));
        Log.d("settings", settings);

        settingsArray[1] = settingsArray[1].trim().replace("]", "");
        //Add's sunny suffix to layout to locate sunny layout
        if(settingsArray[1].equals("Sunny")) {
            return context.getResources().getIdentifier(layoutId+"_sunny", "layout", context.getPackageName());
        }
        //Add's moody suffix to layout to locate moody layout
        else if(settingsArray[1].equals("Moody")) {
            return context.getResources().getIdentifier(layoutId+"_moody", "layout", context.getPackageName());
        }
        //Default layout
        else {
            return context.getResources().getIdentifier(layoutId, "layout", context.getPackageName());
        }
    }

        //Finds the popup of the chosen pallette design by the user
        public static String findPopUp(Context context){
        String settings = saveUserID.grabSettings(context);
        //If nosettings default
        if(settings.equals("settings_.txt")){
            return "";
        }
        //If file does not exist create default settings
        if(!new File(context.getFilesDir(),settings).exists()) {
            try {
                initialiseSettings.initialise(context);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        String[] settingsArray = (ReadData.returnData(context, settings).split(","));
        //Add's sunny suffix to layout to locate sunny layout
        if(settingsArray[1].equals("Sunny")) {
            return "sunny";
        }
        //Add's moody suffix to layout to locate moody layout
        else if(settingsArray[1].equals("Moody")) {
            return "moody";
        }
        //Return blank
        else {
            return "";
        }
    }
}
