package com.example.aiapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class saveButtonFunc{

    public static void funcSaveBtn (Context context, Activity activity, String[] fields, String fileName, Class<?> targetActivity) {
        View saveBtn = activity.findViewById(R.id.saveButton);
        if (saveBtn != null) {
            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SaveData saveData = new SaveData();
                    try {
                        saveData.FileSave(context, DataGrab.gatherData(context, fields), fileName);
                        String data = ReadData.returnData(context, fileName);
                        TextView testView = activity.findViewById(R.id.testView);
                        testView.setText(data);
                        if (targetActivity != null) {
                            Intent intent = new Intent(activity, targetActivity);
                            activity.startActivity(intent);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

        }
    }
    public static void userSaveBtn  (Context context, Activity activity, String[] fields, String fileName, Class<?> targetActivity) {
        View saveBtn = activity.findViewById(R.id.saveButton);
        if (saveBtn != null) {
            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SaveData saveData = new SaveData();
                    try {
                        saveData.FileSave(context, DataGrab.gatherUserData(context, fields), fileName);
                        ReadData readData = new ReadData();
                        String data = readData.returnData(context, fileName);
                        TextView testView = activity.findViewById(R.id.test);
                        testView.setText(data);
                        if (targetActivity != null) {
                            Intent intent = new Intent(activity, targetActivity);
                            activity.startActivity(intent);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

        }
    }
    public static void saveDbBtn (Context context, Activity activity, String[] fields, Class<?> targetActivity) {
        View saveBtn = activity.findViewById(R.id.saveButton);
        if (saveBtn != null) {
            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SaveData saveData = new SaveData();
                    if (!buildDB.checkUser(buildDB.getInstance(context).getReadableDatabase(),DataGrab.gatherData(context, fields))) {
                        try {
                            saveData.saveUserDb(context, DataGrab.gatherData(context, fields));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        try {
                            saveData.updateUserDb(context, DataGrab.gatherData(context, fields));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    if (targetActivity != null) {
                        Intent intent = new Intent(activity, targetActivity);
                        activity.startActivity(intent);
                    }
                }
            });
        }

    }
}
