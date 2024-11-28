package com.example.aiapplication;


import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        File dbFile = this.getDatabasePath("UserDetails");
        if (!dbFile.exists() || !dbFile.canRead()) {
            Log.e("APP_DEBUG", "Database file is missing or not accessible: " + dbFile.getAbsolutePath());
        }
        takeButtonFunc.takeBtn(MainMenu.this, DataCollect.class, R.id.takeToSettings);
        takeButtonFunc.takeBtn(MainMenu.this, RequestPage.class, R.id.takeToRequest);
        takeButtonFunc.takeBtn(MainMenu.this, SettingsPage.class, R.id.takeToSettings);
    }
}