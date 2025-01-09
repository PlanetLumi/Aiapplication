package com.example.aiapplication;

import static android.content.Intent.getIntent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;


import androidx.annotation.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class saveButtonFunc{

    //Default to save all the data in an activity
    public static void funcSaveBtn(Context context, Activity activity, String[] fields, String fileName, Class<?> targetActivity, @Nullable Intent intent) {
        View saveBtn = activity.findViewById(R.id.saveButton);
        if (saveBtn != null) {
            saveBtn.setOnClickListener(v -> {
                try {
                    //Creates file with file name
                    File file = new File(context.getFilesDir(), fileName);
                    if (!file.exists()) {
                        file.createNewFile(); // Ensure the file is created
                    }
                    //Saves inputted data
                    SaveData.FileSave(context, DataGrab.gatherUserData(context, fields), fileName);
                    if (targetActivity != null) {
                        activity.startActivity(intent);
                        activity.finish();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    setPopup.showError(context, "Error saving data", null);
                }
            });
        }
    }
    //Default to save all the data in settings
    public static void userSaveBtn  (Context context, Activity activity, String[] fields, String fileName, String message1, Class<?> targetActivity, boolean recreate) {
        Log.d("userSaveBtn", "userSaveBtn called");
        View saveBtn = activity.findViewById(R.id.saveButton);
        Log.d("userSaveBtn", "saveBtn:" + saveBtn);
        if (saveBtn != null) {
            Log.d("userSaveBtn", "saveBtn != null");
            saveBtn.setOnClickListener(v -> {
                try {
                    Log.d("userSaveBtn", "try block");
                    //Saves data to file
                    SaveData.FileSave(context, DataGrab.gatherUserData(context,fields), fileName);
                    //Success popup
                    setPopup.showSuccess(context, "greetingsbot", message1, null);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                    if (targetActivity != null) {
                        Intent intent = new Intent(activity, targetActivity);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        activity.startActivity(intent);
                        activity.finish();
                    }
                    //Recreates to make changes to settings page when saved
                if (recreate) {
                    activity.recreate();
                }
            });


        }
    }
    //Default to save data to a database
    public static void saveDbBtn (Context context, Activity activity, String[] fields, Class<?> targetActivity) {
        View saveBtn = activity.findViewById(R.id.saveButton);
        if (saveBtn != null) {
            saveBtn.setOnClickListener(v -> {
                SaveData saveData = new SaveData();
                    try {
                        //Sets data to inputted data
                        saveData.updateUserDb(context, DataGrab.gatherData(context, fields));
                        setPopup.showSuccess(context, "greetingsbot", "Data Saved Successfully!", null);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                }
                if (targetActivity != null) {
                    Intent intent = new Intent(activity, targetActivity);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    activity.startActivity(intent);
                    activity.finish();
                }
            });
        }
    }
}
