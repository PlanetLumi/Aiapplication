package com.example.aiapplication;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.IOException;

public class createUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.create_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.newUser), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button createButton = findViewById(R.id.createButton);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifyPassword.verify(DataGrab.gatherUserData(getApplicationContext(), new String[]{"Password","verifyPassword"}))) {
                    try {
                        SaveData.saveUserDb(getApplicationContext(), DataGrab.gatherUserData(getApplicationContext(), new String[]{"userName", "Password"}));
                        Intent intent = new Intent(createUser.this, LoginPage.class);
                        startActivity(intent);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                        Log.d("Error", "Passwords do not match");
                    }
            }
        });
    }
}
