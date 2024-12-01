package com.example.aiapplication;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

public class setBoxFunc{
    public static void setBoxes(Activity activity, String[] idNames){
        for (int i = 0; i < idNames.length; i++) {
            int boxGrab = activity.getResources().getIdentifier(idNames[i], "id", activity.getPackageName());
            setBox(activity, boxGrab, idNames[i]);
        }

    }
    public static void setBox(Activity activity, int boxId, String boxName){

        Context context = activity.getApplicationContext();
        final android.widget.EditText box = activity.findViewById(boxId);
        if (box == null) {
            Log.e("setBoxFunc", "EditText with ID " + boxId + " not found.");
            return;
        }
        String data = (buildDB.readDB(buildDB.getInstance(context).getReadableDatabase(), new String[]{boxName}, saveUserID.grabID(context)));
        Log.d("Data", "Setting data for boxName " + boxName + ": " + data);
        if(data.isEmpty()){
            box.setHint(returnBoxHint(boxName));
        } else {
            box.setText(data);
        }
    }

    public static String returnBoxHint(String boxName) {
        switch (boxName) {
            case "Title":
                return "Title";
            case "FName":
                return "First Name";
            case "SName":
                return "Surname";
            case "PNumber":
                return "Phone Number";
            case "Address":
                return "Address";
        }
        return "";
    }


}
