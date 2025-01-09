package com.example.aiapplication;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;

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
        //Sets up spinners and switches
        String settings = saveUserID.grabSettings(SettingsPage.this);
        spinnerFunc.setSpinners(SettingsPage.this, new String[]{"stylePalette"}, new int[] {R.array.style_palette},settings);
        switchFunc.setSwitches(SettingsPage.this, new String[] {"permissions"}, settings);
        //Defines save parameters and sets success popup
        saveButtonFunc.userSaveBtn(SettingsPage.this, SettingsPage.this, new String[]{"stylePalette", "permissions"}, settings, "Settings Saved Successfully!", null, true);
        ExitButtonFunc.exitBtn(SettingsPage.this, MainMenu.class);
        Button LogOut = findViewById(R.id.logoutButton);
        //Sets custom log out button that clears all the data for the user and goes to login page
        LogOut.setOnClickListener(view -> {
            saveUserID.clearID(SettingsPage.this);
            startActivity(new Intent(SettingsPage.this, LoginPage.class));
        });
    }
}