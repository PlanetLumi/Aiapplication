package com.example.aiapplication;

import android.content.pm.ActivityInfo;
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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        int layoutId = setPalette.setLayout(SettingsPage.this, "settings_page");
        setContentView(layoutId);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.settings), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        String settings = saveUserID.grabSettings(SettingsPage.this);


        spinnerFunc.setSpinners(SettingsPage.this, new String[]{"stylePalette"}, new int[] {R.array.style_palette},settings);
        switchFunc.setSwitches(SettingsPage.this, new String[] {"permissions"}, settings);
        saveButtonFunc.userSaveBtn(SettingsPage.this, SettingsPage.this, new String[]{"stylePalette", "permissions"}, settings, "Settings Saved Successfully!",null);
        ExitButtonFunc.exitBtn(SettingsPage.this, MainMenu.class);
    }
}