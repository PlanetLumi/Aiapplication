package com.example.aiapplication;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;

public class  takeButtonFunc {
    public static void takeBtn(Activity activity, Class<?> targetActivity, int buttonId, @Nullable Runnable customAction) {
        View takeBtn = activity.findViewById(buttonId);
            if (takeBtn != null) {
                takeBtn.setOnClickListener(v -> {
                    //Allows for custom function defined for button
                    if (customAction != null) {
                        customAction.run();
                    }
                    // Transition to the target activity
                    Intent intent = new Intent(activity, targetActivity);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    activity.startActivity(intent);
                    activity.finish();
                });
            }
    }
}

