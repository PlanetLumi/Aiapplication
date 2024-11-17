package com.example.aiapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class saveButtonFunc{

    public static void saveBtn  (Context context, Activity activity, String[] fields, String fileName) {
        final Button saveBtn = activity.findViewById(R.id.saveButton);
        if (saveBtn != null) {
            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SaveData saveData = new SaveData();
                    try {
                        saveData.FileSave(context, DataGrab.gatherData(context, fields), fileName);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }
}
