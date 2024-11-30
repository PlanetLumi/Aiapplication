package com.example.aiapplication;

import android.app.Activity;
import android.content.Context;

public class setBoxFunc{
    public static void setBoxes(Activity activity, String[] idNames){
        for (int i = 0; i < idNames.length; i++) {
            setBox(activity, idNames[i]);
        }

    }
    public static void setBox(Activity activity, String boxId){
        Context context = activity.getApplicationContext();
        int boxGrab = activity.getResources().getIdentifier(boxId, "id", activity.getPackageName());
        final android.widget.EditText box = activity.findViewById(boxGrab);
        String data = buildDB.readDB(context.openOrCreateDatabase("UserDetails", Context.MODE_PRIVATE, null), "UserDetails",new String[]{boxId}, saveUserID.grabID(context));
        if(data.isEmpty() || data == null || data.equals(String.valueOf(null))){
            box.setHint(boxId);
        } else {
            box.setText(data);
        }
    }

}
