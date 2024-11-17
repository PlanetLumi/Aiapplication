package com.example.aiapplication;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class takeButtonFunc {
    public static void takeBtn  (Activity activity, Class<?> targetActivity, int buttonId) {
        final Button takeBtn = activity.findViewById(buttonId);
        if (takeBtn != null) {
            takeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, targetActivity);
                    activity.startActivity(intent);
                }
            });
        }
    }
}

