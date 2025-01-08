package com.example.aiapplication;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class LoginPage extends AppCompatActivity {
    private static final int MAX_ATTEMPTS = 10;
    private static final int WARNING_ATTEMPTS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int layoutId = setPalette.setLayout(LoginPage.this, "login_page");
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(layoutId);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.loginpage), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SQLiteDatabase readableDatabase = buildDB.getInstance(LoginPage.this).getReadableDatabase();
        SQLiteDatabase writableDatabase = buildDB.getInstance(LoginPage.this).getWritableDatabase();

        ImageButton loginButton = findViewById(R.id.loginButton);
        ImageButton daniButton = findViewById(R.id.daniButton);
        daniButton.setOnClickListener(v -> {
            setPopup.showDani(LoginPage.this);
                });

        loginButton.setOnClickListener(v -> {
            String[] details = DataGrab.gatherData(LoginPage.this, new String[]{"userName", "Password"});
            String normalizedUsername = details[0].replace(",", "").toLowerCase();
            if (details[0].isEmpty() || details[1].isEmpty()) {
                setPopup.showError(LoginPage.this, "Invalid Login", "Please enter a username and password.");
                return;
            }
            if (!normalizedUsername.matches("^[a-zA-Z0-9_.]+$")) {
                setPopup.showError(LoginPage.this, "Invalid Login", "Username cannot contain special characters - only alphanumerics, underscores, and periods are allowed.");
                return;
            }
            if (!buildDB.checkIfUserExists(readableDatabase, normalizedUsername)) {
                setPopup.showError(LoginPage.this, "Invalid Login", "This account does not exist.");
                return;
            }
            if (buildDB.checkUserLockedTime(readableDatabase, normalizedUsername)) {
                failedLogin(readableDatabase, writableDatabase, normalizedUsername);
                return;
            }
            long user = buildDB.loginUser(readableDatabase, normalizedUsername, details[1]);
            if (user != -1) {
                saveUserID.saveID(LoginPage.this, user);
                saveUserID.saveName(LoginPage.this, normalizedUsername);
                buildDB.resetLoginAttempts(readableDatabase, normalizedUsername);
                Intent intent = new Intent(LoginPage.this, MainMenu.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else {
                failedLogin(readableDatabase, writableDatabase, normalizedUsername);
            }
        });
        takeButtonFunc.takeBtn(this, createUser.class, R.id.takeToCreate, null);
    }

    private void failedLogin(SQLiteDatabase readableDatabase, SQLiteDatabase writableDatabase, String username) {
        buildDB.incrementLoginAttempts(writableDatabase, username);
        int attempts = buildDB.getLoginAttempts(readableDatabase, username);
        if (attempts >= MAX_ATTEMPTS) {
            setPopup.showError(LoginPage.this, "ACCOUNT LOCKED", "This account has been permanently locked, please contact an administrator.");
        } else if (attempts > WARNING_ATTEMPTS) {
            buildDB.lockUser(writableDatabase, username);
            setPopup.showError(LoginPage.this, "Too many attempts!", "This account has been locked, please wait " + buildDB.findCurrentDuration(readableDatabase, username) + " minutes.");
        } else {
            setPopup.showError(LoginPage.this, "Invalid Login", "Try again, or create an account! You have " + (WARNING_ATTEMPTS - attempts) + " attempt(s) left.");
        }
    }
}

