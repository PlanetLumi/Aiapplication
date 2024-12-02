package com.example.aiapplication;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Objects;

public class spinnerFunc {
    public static void setSpinners (Activity activity, String[] idNames, int[] arrayId,String fileName){
        String[] data = (((ReadData.returnData(activity, fileName)).replace("[", "").replace("]", "")).split(","));
        for (int i = 0; i < idNames.length; i++) {
            setSpinner(activity, idNames[i], arrayId[i], data);
        }
    }
    public static void setSpinner (Activity activity, String spinnerId, int arrayId, String[] data){
        int SpinnerGrab = activity.getResources().getIdentifier(spinnerId, "id", activity.getPackageName());
        final Spinner spinner = activity.findViewById(SpinnerGrab);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                activity,
                arrayId,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        if (data != null) {
            for(int i = 0; i < data.length; i++){
                if(data[i].replace(",", "").trim().equals(String.valueOf(spinnerId))){
                    String savedValue = data[i+1];
                    for (int position = 0; position < adapter.getCount(); position++) {
                        if (Objects.requireNonNull(adapter.getItem(position)).toString().equals(savedValue)) {
                            spinner.setSelection(position);
                            break;
                        }
                    }
                }
            }
        }
    }
}
