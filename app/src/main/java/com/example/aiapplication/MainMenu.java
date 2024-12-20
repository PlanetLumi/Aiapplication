package com.example.aiapplication;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.activity.EdgeToEdge;
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
        setContentView(layoutId);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        takeButtonFunc.takeBtn(MainMenu.this, DataCollect.class, R.id.takeToSettings, null);
        takeButtonFunc.takeBtn(MainMenu.this, RequestPage.class, R.id.takeToRequest, () -> {
            try {
                SaveData.saveOption(MainMenu.this, "1", "userChoice.txt");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        takeButtonFunc.takeBtn(MainMenu.this, RequestPage.class, R.id.takeToComplaints, () -> {
            try {
                SaveData.saveOption(MainMenu.this, "2", "userChoice.txt");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        takeButtonFunc.takeBtn(MainMenu.this, SettingsPage.class, R.id.takeToSettings, null);
        takeButtonFunc.takeBtn(MainMenu.this, DataCollect.class, R.id.takeToDetails, null);
    }

}