
package com.example.aiapplication;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataCollect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.data_collect);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.datacollect), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        saveButtonFunc.saveDbBtn(DataCollect.this, DataCollect.this, new String[]{"FName", "SName", "PNumber", "Address"}, null);
        TextView test = findViewById(R.id.testView);
        test.setText(ReadData.returnDBUserData(DataCollect.this));
        ExitButtonFunc.exitBtn(DataCollect.this, MainActivity.class);

    }
}