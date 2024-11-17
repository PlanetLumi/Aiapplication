package com.example.aiapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;

public class RequestPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.requestpage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.requestPage), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        final Button makeRequestBtn = findViewById(R.id.requestButton);
        makeRequestBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SaveData saveData = new SaveData();
                try {
                    saveData.FileSave(RequestPage.this, DataGrab.gatherData(RequestPage.this, new String[]{"companyInp", "detailsInp"}), "userRequestInput.txt");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ReadData readData = new ReadData();
                TextView testDisplay = findViewById(R.id.testS);
                testDisplay.setText(readData.returnData(RequestPage.this, "userRequestInput.txt"));
                startActivity(new Intent(RequestPage.this, GenerationArea.class));
            }
        });
        final Button exitBtn = findViewById(R.id.exitButton);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(RequestPage.this, MainActivity.class));
            }
        });
    }
}