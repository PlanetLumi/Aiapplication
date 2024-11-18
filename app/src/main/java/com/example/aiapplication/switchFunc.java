package com.example.aiapplication;

import static com.example.aiapplication.spinnerFunc.setSpinner;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Collection;
import java.util.List;

public class switchFunc {
    public static void setSwitches(Activity activity, String[] idNames, String fileName) {
        ReadData readData = new ReadData();
        String[] data = (((readData.returnData(activity, fileName)).replace("[", "").replace("]", "")).split(","));
        for (int i = 0; i < idNames.length; i++) {
            switchBtn(activity, idNames[i], data);
        }
    }
    public static void switchBtn(Activity activity, String buttonId, String[] data) {
        int buttonGrab = activity.getResources().getIdentifier(buttonId, "id", activity.getPackageName());
        final Switch switchBtn = activity.findViewById(buttonGrab);
        if (data != null) {
            for (int i = 0; i < data.length; i++) {
                if(data[i].trim().equals(String.valueOf(buttonId))){
                    String savedValue = data[i+1].trim();
                    if(savedValue.equals("true")) {
                        switchBtn.setChecked(true);
                    }else{
                        switchBtn.setChecked(false);
                    }
                }
            }
        }
    }
}
