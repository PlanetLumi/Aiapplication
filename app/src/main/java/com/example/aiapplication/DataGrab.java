package com.example.aiapplication;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.SwitchCompat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataGrab extends AppCompatActivity {

    public static Collection<Integer> fillDataGrabs(Context context, String[] idNames) {
        List<Integer> dataPoints = new ArrayList<>();
        for (int i = 0; i < idNames.length; i++) {
            int resourceId = context.getResources().getIdentifier(idNames[i], "id", context.getPackageName());
            dataPoints.add(resourceId);
        }
        return dataPoints;
    }

    public static String[] gatherData(Context context, String[] idNames) {
        List<Integer> dataPoints = (List<Integer>) fillDataGrabs(context, idNames);
        String[] userData = new String[dataPoints.size()];
        Activity activity = (Activity) context;

        for (int i = 0; i < dataPoints.size(); i++) {
            View current = activity.findViewById(dataPoints.get(i));
            if (current instanceof EditText) {
                // Get text from EditText and add to list
                String text = ((EditText) current).getText().toString();
                userData[i] = text;
            }
        }

        return userData; // Return as a String array
    }
    public static String gatherUserData(Context context, String[] idNames) {
        Log.d("DataGrab", "gatherUserData called");
        List<Integer> dataPoints = (List<Integer>) fillDataGrabs(context, idNames);
        Log.d("DataGrab", "Data Points: " + dataPoints.toString());
        List<Object> userData = new ArrayList<>();
        Log.d("DataGrab", "User Data: " + userData.toString());
        for (int i = 0; i < dataPoints.size(); i++) {
            View current = ((AppCompatActivity) context).findViewById(dataPoints.get(i));
            Log.d("DataGrab", "Current View: " + current.getClass().getSimpleName());
            if (current instanceof EditText) {
                String data = idNames[i] + "," +  (((EditText) current).getText().toString());
                userData.add(data);
            }
            if (current instanceof AppCompatSpinner) {
                String data = idNames[i] + "," + (((AppCompatSpinner) current).getSelectedItem().toString());
                userData.add(data);
            }
            if (current instanceof SwitchCompat) {
                String data = idNames[i] + "," +  (((SwitchCompat) current).isChecked());
                userData.add(data);
            }
            if (current instanceof CheckBox) {
                String data = idNames[i] + "," +  (((CheckBox) current).isChecked());
                userData.add(data);
            }
        }
        return userData.toString();
    }
}

