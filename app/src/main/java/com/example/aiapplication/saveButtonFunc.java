package com.example.aiapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;


import java.io.IOException;

public class saveButtonFunc{

    public static void funcSaveBtn (Context context, Activity activity, String[] fields, String fileName, Class<?> targetActivity) {
        View saveBtn = activity.findViewById(R.id.saveButton);
        if (saveBtn != null) {
            saveBtn.setOnClickListener(v -> {
                try {
                    SaveData.FileSave(context, DataGrab.gatherUserData(context, fields), fileName);
                    if (targetActivity != null) {
                        Intent intent = new Intent(activity, targetActivity);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        activity.startActivity(intent);
                        activity.finish();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

        }
    }
    public static void userSaveBtn  (Context context, Activity activity, String[] fields, String fileName, Class<?> targetActivity) {
        View saveBtn = activity.findViewById(R.id.saveButton);
        if (saveBtn != null) {
            saveBtn.setOnClickListener(v -> {
                try {
                    SaveData.FileSave(context, DataGrab.gatherUserData(context,fields), fileName);
                    if (targetActivity != null) {
                        Intent intent = new Intent(activity, targetActivity);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        activity.startActivity(intent);
                        activity.finish();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

        }
    }
    public static void saveDbBtn (Context context, Activity activity, String[] fields, Class<?> targetActivity) {
        View saveBtn = activity.findViewById(R.id.saveButton);
        if (saveBtn != null) {
            saveBtn.setOnClickListener(v -> {
                SaveData saveData = new SaveData();
                    try {
                        saveData.updateUserDb(context, DataGrab.gatherData(context, fields));
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
