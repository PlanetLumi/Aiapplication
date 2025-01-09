package com.example.aiapplication;
import android.app.Activity;
import androidx.appcompat.widget.SwitchCompat;

public class switchFunc {
    //Sets switches from file
    public static void setSwitches(Activity activity, String[] idNames, String fileName) {
        String[] data = (((ReadData.returnData(activity, fileName)).replace("[", "").replace("]", "")).split(","));
        for (String idName : idNames) {
            switchBtn(activity, idName, data); // Pass the data array to switchBtn
        }
    }
    //Sets switches from data
    public static void switchBtn(Activity activity, String buttonId, String[] data) {
        int buttonGrab = activity.getResources().getIdentifier(buttonId, "id", activity.getPackageName());
        final SwitchCompat switchBtn = activity.findViewById(buttonGrab);
        if (data != null) {
            for (int i = 0; i < data.length; i++) {
                if(data[i].trim().equals(String.valueOf(buttonId))){
                    String savedValue = data[i+1].trim();
                    switchBtn.setChecked(savedValue.equals("true"));
                }
            }
        }
    }
}
