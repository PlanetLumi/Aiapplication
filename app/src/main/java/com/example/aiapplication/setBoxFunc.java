package com.example.aiapplication;

import android.app.Activity;
import android.content.Context;


public class setBoxFunc{
    public static void setBoxes(Activity activity, String[] idNames){
        for (String idName : idNames) {
            int boxGrab = activity.getResources().getIdentifier(idName, "id", activity.getPackageName());
            setBox(activity, boxGrab, idName);
        }

    }
    public static void setBox(Activity activity, int boxId, String boxName){

        Context context = activity.getApplicationContext();
        final android.widget.EditText box = activity.findViewById(boxId);
        if (box == null) {
            return;
        }
        String data = (buildDB.readDB(buildDB.getInstance(context).getReadableDatabase(), new String[]{boxName}, saveUserID.grabID(context)));
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
