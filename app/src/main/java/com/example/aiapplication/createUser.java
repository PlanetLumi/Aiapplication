package com.example.aiapplication;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.io.IOException;
import android.widget.ImageButton;

public class createUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        int layoutId = setPalette.setLayout(createUser.this, "create_user");
        setContentView(layoutId);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.newUser), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageButton createButton = findViewById(R.id.createButton);
        createButton.setOnClickListener(v -> createAccount());
        takeButtonFunc.takeBtn(createUser.this, LoginPage.class, R.id.takeToLogin, null);
    }

    //main account creation function
    private void createAccount() {
        //creates static instance
        SQLiteDatabase readableDatabase = buildDB.getInstance(createUser.this).getReadableDatabase();
        //Finds inputted data and adds it to string array
        String[] userData = DataGrab.gatherData(createUser.this, new String[]{"userName", "Password", "verifyPassword"});
        //split data
        String username = userData[0];
        String password = userData[1];

        //checks to see if input made
        if (username.isEmpty() || password.isEmpty()) {
            setPopup.showError(createUser.this, "Please fill in all fields!", null);
            return; //breaks
        }
        //CHeck to see if new creation is valid in table
        if (buildDB.checkSpam(readableDatabase)) {
            setPopup.showError(createUser.this, "You have made too many accounts!", "Please contact an administrator");
            return;
        }
        //Sanitise input to prevent SQL injection, almost no special characters
        if (!username.matches("^[a-zA-Z0-9_.]+$")) {
            setPopup.showError(createUser.this, "Invalid Login", "Username cannot contain special characters");
            return;
        }
        //Set verified to true and try to invalidate
        boolean verified = true;
        //checks to see if username already exists as cannot have duplicates
        if (buildDB.checkIfUserExists(readableDatabase, username)) {
            setPopup.showError(createUser.this, "Username already exists!", "Try another name or log-in!");
            return;
        }
        //check valid verification input
        if (!verifyPassword.verifyMatch(DataGrab.gatherData(createUser.this, new String[]{"Password", "verifyPassword"}))) {
            setPopup.showError(createUser.this, "Passwords do not match!", null);
            return;
        }
        //run through function that checks password requirements
        for (String flag : verifyPassword.allFlags(DataGrab.gatherData(createUser.this, new String[]{"Password"})[0])) {
            if (!flag.equals("True")) {
                //Set new popup for each error
                setPopup.showError(createUser.this, "Password does not meet requirements", flag);
                verified = false; //break
            }
        }
        //If all invalidation checks fail then account can be used
        if (verified) {
            try {
                //Saves data to array
                SaveData.saveUserDb(createUser.this, DataGrab.gatherData(createUser.this, new String[]{"userName", "Password"}));
                //Return to login
                Intent intent = new Intent(createUser.this, LoginPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
