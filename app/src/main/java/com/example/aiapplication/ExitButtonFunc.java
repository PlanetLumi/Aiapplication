package com.example.aiapplication;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class ExitButtonFunc{

    //Button that is predefined to take to previous activity
    public static void exitBtn  (Activity activity, Class<?> targetActivity) {
        View exitBtn = activity.findViewById(R.id.exitButton);
        if (exitBtn != null) {
            exitBtn.setOnClickListener(v -> {
                Intent intent = new Intent(activity, targetActivity);
                activity.startActivity(intent);
            });
        }
    }
}
