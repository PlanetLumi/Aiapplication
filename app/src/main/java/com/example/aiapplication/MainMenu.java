package com.example.aiapplication;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.IOException;

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
        ImageButton takeToRequest = findViewById(R.id.takeToRequest);
        takeToRequest.setOnClickListener(v -> {
            try {
                SaveData.FileSave(MainMenu.this, "1", "userChoice.txt");
                takeButtonFunc.takeBtn(MainMenu.this, RequestPage.class, R.id.takeToRequest);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
        ImageButton takeToComplaints = findViewById(R.id.takeToComplaints);
        takeToComplaints.setOnClickListener(v -> {
            try {
                SaveData.FileSave(MainMenu.this, "2", "userChoice.txt");
                takeButtonFunc.takeBtn(MainMenu.this, RequestPage.class, R.id.takeToRequest);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        takeButtonFunc.takeBtn(MainMenu.this, SettingsPage.class, R.id.takeToSettings);
    }
}