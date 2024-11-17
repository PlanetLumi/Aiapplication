package com.example.aiapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class ExitButtonFunc{

    public static void ExitBtn  (Activity activity, Class<?> targetActivity) {
        Context context;
        final Button exitBtn = activity.findViewById(R.id.exitButton);
        if (exitBtn != null) {
            exitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, MainActivity.class);
                    activity.startActivity(intent);
                }
            });
        }
    }
}
