package com.example.aiapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileNotFoundException;

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
        if(!new File(getFilesDir(),"settings.txt").exists()) {
            try {
                initialiseSettings.initialise(SettingsPage.this);
                Log.d("Settings", "Settings file created");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        spinnerFunc.setSpinners(SettingsPage.this, new String[]{"stylePalette"}, new int[] {R.array.style_palette},"settings.txt");
        switchFunc.setSwitches(SettingsPage.this, new String[] {"permissions","notifications"}, "settings.txt");
        saveButtonFunc.userSaveBtn(SettingsPage.this, SettingsPage.this, new String[]{"stylePalette", "permissions", "notifications"}, "settings.txt", null);
        setPopup.setSuccessButton(SettingsPage.this, "greetinggrey", R.id.saveButton, "Settings saved!", null);
        ExitButtonFunc.exitBtn(SettingsPage.this, MainMenu.class);
    }
}