
package com.example.aiapplication;
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
import java.util.List;

public class DataCollect extends AppCompatActivity {
    public String gatherData() {
        TextView testDisplay = findViewById(R.id.test);
        int[] dataPoints = {R.id.FName, R.id.SName, R.id.age, R.id.Region};
        List<Object> userData = new ArrayList<>();
        for(int i = 0; i < dataPoints.length; i++) {
            EditText current = (EditText) findViewById(dataPoints[i]);
            userData.add(current.getText().toString());
        }
        testDisplay.setText(userData.toString());
        return userData.toString();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.data_collect);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button save = findViewById(R.id.Save);
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                 SaveData saveData = new SaveData();
            try {
               saveData.FileSave(DataCollect.this, gatherData());
            } catch (IOException e) {
            throw new RuntimeException(e);
            }
            ReadData readData = new ReadData();
            TextView testDisplay = findViewById(R.id.test);
            testDisplay.setText(readData.returnData(DataCollect.this, "UserData.txt"));
          }
        });

        Button exit = findViewById(R.id.Exit);
        exit.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick (View v){
                    startActivity(new Intent(DataCollect.this,MainActivity.class));
            }
        });
    }
}