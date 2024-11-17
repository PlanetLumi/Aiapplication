package com.example.aiapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SettingsPage extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.settingspage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.settings), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        spinnerFunc.setSpinner(SettingsPage.this, R.id.stylePalette, R.array.style_palette);
        saveButtonFunc.saveBtn(SettingsPage.this, SettingsPage.this, new String[]{"stylePalette", "permissions", "notifications"}, "settings.txt");
        ExitButtonFunc.exitBtn(SettingsPage.this, MainActivity.class);
    }
}