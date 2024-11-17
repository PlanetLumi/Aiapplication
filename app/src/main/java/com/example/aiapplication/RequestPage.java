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
        setContentView(R.layout.fillrequest);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.fillRequest), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        saveButtonFunc.saveBtn(RequestPage.this,RequestPage.this, new String[]{"reqDomain", "infoBox"}, "userRequestInput.txt");
        takeButtonFunc.takeBtn(RequestPage.this, GenerationArea.class, R.id.saveButton);
        TextView testView = findViewById(R.id.testView);
        String fileContent = ReadData.returnData(RequestPage.this, "userRequestInput.txt");
        if (fileContent.isEmpty()) {
            testView.setText("No data available");
        } else {
            String[] split = fileContent.split(",");
            if (split.length >= 2) {
                testView.setText(split[0] + " " + split[1]);
            } else {
                testView.setText("Incomplete data");
            }
        }
        ExitButtonFunc.exitBtn(RequestPage.this, MainActivity.class);
    }
}