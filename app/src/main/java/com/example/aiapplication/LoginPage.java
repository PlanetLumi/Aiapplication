package com.example.aiapplication;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

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



        ImageButton loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] details = DataGrab.gatherData(LoginPage.this, new String[]{"userName", "Password"});
                String normalizedUsername = details[0].replace(",", "").toLowerCase();
                if(!buildDB.checkIfUserExists(buildDB.getInstance(LoginPage.this).getReadableDatabase(), normalizedUsername)){
                    errorPopup.showError(LoginPage.this, "Invalid Login", "This account does not exist.");
                    return;
                }
                if(buildDB.checkUserLockedTime(buildDB.getInstance(LoginPage.this).getReadableDatabase(), normalizedUsername)){
                    if(buildDB.getLoginAttempts(buildDB.getInstance(LoginPage.this).getReadableDatabase(), normalizedUsername) >= 10){
                        errorPopup.showError(LoginPage.this, "ACCOUNT LOCKED!", "This account has been permanently locked, please contact an administrator.");
                        return;
                    }
                    errorPopup.showError(LoginPage.this, "Too many attempts!", "This account has been locked, please wait " + buildDB.findCurrentDuration(buildDB.getInstance(LoginPage.this).getReadableDatabase(), normalizedUsername) + " minutes.");
                    return;
                }
                long user = buildDB.loginUser(buildDB.getInstance(LoginPage.this).getReadableDatabase(), normalizedUsername, details[1]);
                if(user != -1){
                   saveUserID.saveID(LoginPage.this, user);
                   buildDB.resetLoginAttempts(buildDB.getInstance(LoginPage.this).getWritableDatabase(), normalizedUsername);
                   Intent intent = new Intent(LoginPage.this, MainMenu.class);
                   startActivity(intent);
                } else {
                    buildDB.incrementLoginAttempts(buildDB.getInstance(LoginPage.this).getWritableDatabase(),normalizedUsername);
                    int attempts = buildDB.getLoginAttempts(buildDB.getInstance(LoginPage.this).getReadableDatabase(), normalizedUsername);
                    if (attempts > 3){
                        if(buildDB.getLoginAttempts(buildDB.getInstance(LoginPage.this).getReadableDatabase(), normalizedUsername) >= 10){
                            errorPopup.showError(LoginPage.this, "ACCOUNT LOCKED!", "This account has been permanently locked, please contact an administrator.");
                            return;
                        }
                        buildDB.lockUser(buildDB.getInstance(LoginPage.this).getWritableDatabase(), normalizedUsername);
                        errorPopup.showError(LoginPage.this, "Too many attempts!", "This account has been locked, please wait " + buildDB.findCurrentDuration(buildDB.getInstance(LoginPage.this).getReadableDatabase(), normalizedUsername) + " minutes.");
                        return;
                    }else {
                        String message2 = "Try again, or create an account! You have " + (3 - attempts) +  " attempt(s) left.";
                        errorPopup.showError(LoginPage.this, "Invalid Login", message2);
                        return;
                    }
                }
            }
        });
        takeButtonFunc.takeBtn(this, createUser.class, R.id.takeToCreate);
    }
}

