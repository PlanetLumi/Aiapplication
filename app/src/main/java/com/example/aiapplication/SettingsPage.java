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
        int layoutId = setPalette.setLayout(SettingsPage.this, "settings_page");
        setContentView(layoutId);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.settings), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        spinnerFunc.setSpinners(SettingsPage.this, new String[]{"stylePalette"}, new int[] {R.array.style_palette},"settings.txt");
        switchFunc.setSwitches(SettingsPage.this, new String[] {"permissions","notifications"}, "settings.txt");
        saveButtonFunc.userSaveBtn(SettingsPage.this, SettingsPage.this, new String[]{"stylePalette", "permissions", "notifications"}, "settings.txt", "Settings Saved Successfully!",null);
        ExitButtonFunc.exitBtn(SettingsPage.this, MainMenu.class);
    }
}