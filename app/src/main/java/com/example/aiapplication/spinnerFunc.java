package com.example.aiapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Arrays;

public class spinnerFunc {
    public static void setSpinner (Activity activity, int spinnerId, int arrayId, String fileName){
        final Spinner spinner = activity.findViewById(spinnerId);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                activity,
                arrayId,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        if (fileName != null) {
            ReadData readData = new ReadData();
            String[] data = (((readData.returnData(activity, fileName)).replace("[", "").replace("]", "")).split(","));
            for(int i = 0; i < data.length; i++){
                if(data[i].equals("Spinner")){
                    String savedValue = data[i+1];
                    for (int position = 0; position < adapter.getCount(); position++) {
                        if (adapter.getItem(position).toString().equals(savedValue)) {
                            spinner.setSelection(position);
                            break;
                        }
                    }
                }
            }
        }
    }
}
