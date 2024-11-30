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
import android.os.Handler;

public class createUser extends AppCompatActivity {
    private boolean buttonActive = true;

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
                if (DataGrab.gatherData(createUser.this, new String[]{"userName", "Password"})[0].isEmpty() || DataGrab.gatherData(createUser.this, new String[]{"userName", "Password"})[1].isEmpty()) {
                    errorPopup.showError(createUser.this, "Please fill in all fields!", null);
                    return;
                }else {
                    if (buildDB.checkSpam((buildDB.getInstance(createUser.this).getReadableDatabase()), "UserCredentials")){
                        errorPopup.showError(createUser.this, "You have made too many accounts!", "Please contact an administrator");
                        return;
                    }
                    boolean verified = true;
                    if (!buildDB.checkIfUserExists(buildDB.getInstance(createUser.this).getReadableDatabase(), DataGrab.gatherData(createUser.this, new String[]{"userName"})[0])) {
                        if (verifyPassword.verifyMatch(DataGrab.gatherData(createUser.this, new String[]{"Password", "verifyPassword"}))) {
                            for (String flag : verifyPassword.allFlags(DataGrab.gatherData(createUser.this, new String[]{"Password"})[0])) {
                                if (!flag.equals("True")) {
                                    errorPopup.showError(createUser.this, "Password does not meet requirements", flag);
                                    verified = false;
                                }
                            }
                            if (verified) {
                                try {
                                    SaveData.saveUserDb(createUser.this, DataGrab.gatherData(createUser.this, new String[]{"userName", "Password"}));
                                    Intent intent = new Intent(createUser.this, LoginPage.class);
                                    startActivity(intent);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            } else {
                                Log.d("Error", "Password does not meet requirements");
                            }
                        } else {
                            errorPopup.showError(createUser.this, "Passwords do not match!", null);
                        }
                    } else {
                        errorPopup.showError(createUser.this, "Username already exists!", "Try another name or log-in!");
                    }
                }

            }
        });
        takeButtonFunc.takeBtn(createUser.this, LoginPage.class, R.id.takeToLogin);
    }
}
