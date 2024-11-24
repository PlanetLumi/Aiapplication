package com.example.aiapplication;


import android.app.Activity;
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

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.loginpage), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String details = DataGrab.gatherUserData(LoginPage.this, new String[]{"userName", "Password"});
                String[] detailsArray = details.split(",");
                long user = buildDB.loginUser(buildDB.getInstance(LoginPage.this, "UserCredentials.db").getReadableDatabase(),detailsArray[0], detailsArray[1]);
                if (user != 1){
                    saveUserID.saveID(LoginPage.this, user);
                    Intent intent = new Intent(LoginPage.this, MainMenu.class);
                    startActivity(intent);
                } else{
                    Log.d("Login", "Login failed");
                }
            }
        });
        takeButtonFunc.takeBtn(this, createUser.class, R.id.takeToCreate);
    }
}

