package com.example.aiapplication;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.io.IOException;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        int layoutId = setPalette.setLayout(MainMenu.this, "main_menu");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(layoutId);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //Check API keys
        RequestToast(MainMenu.this);
        takeButtonFunc.takeBtn(MainMenu.this, DataCollect.class, R.id.takeToDetails, null);
        //Adds extra function to button, saves choice to file
        takeButtonFunc.takeBtn(MainMenu.this, RequestPage.class, R.id.takeToRequest, () -> {
            try {
                SaveData.saveOption(MainMenu.this, "1", "userChoice.txt");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        //Adds extra function to button, saves choice to file
        takeButtonFunc.takeBtn(MainMenu.this, RequestPage.class, R.id.takeToComplaints, () -> {
            try {
                SaveData.saveOption(MainMenu.this, "2", "userChoice.txt");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        takeButtonFunc.takeBtn(MainMenu.this, SettingsPage.class, R.id.takeToSettings, null);
    }
    public static void RequestToast(Context context){
        // Create an AlertDialog to collect API keys from the user
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Enter API Keys");

        // Create a layout to hold input fields for the keys
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 10);

        // Create input fields for the ChatGPT and Hunter IO keys
        final EditText chatGptKeyInput = new EditText(context);
        chatGptKeyInput.setHint("Enter ChatGPT API Key");
        layout.addView(chatGptKeyInput);

        final EditText hunterIoKeyInput = new EditText(context);
        hunterIoKeyInput.setHint("Enter Hunter IO API Key");
        layout.addView(hunterIoKeyInput);

        builder.setView(layout);

        // Set up the "Submit" button
        builder.setPositiveButton("Submit", (dialog, which) -> {
            // Retrieve user input
            String chatGptKey = chatGptKeyInput.getText().toString().trim();
            saveUserID.saveKeys(context, chatGptKey, "chatGptKey");
            String hunterIoKey = hunterIoKeyInput.getText().toString().trim();
            saveUserID.saveKeys(context, hunterIoKey, "hunterIoKey");
            Toast.makeText(context, "ChatGPT Key: " + chatGptKey + "\nHunter IO Key: " + hunterIoKey,
                    Toast.LENGTH_LONG).show();
        });

        // Set up the "Cancel" button
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        // Show the dialog
        builder.show();
    }

}

