package com.example.aiapplication;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class  takeButtonFunc {
    public static void takeBtn  (Activity activity, Class<?> targetActivity, int buttonId) {
            View takeBtn = activity.findViewById(buttonId);
            if (takeBtn != null) {
                takeBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, targetActivity);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        activity.startActivity(intent);
                        activity.finish();
                    }
                });
            }
        }
}

