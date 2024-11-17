package com.example.aiapplication;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GenerationArea extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.generation_area);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.generationArea), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView grabbedEmail = findViewById(R.id.grabbedEmail);
        HunterGenCall hunterGenCall = new HunterGenCall();
        hunterGenCall.fetchEmail(this, "Google.com", new HunterGenCall.EmailResultCallBack() {
            @Override
            public void onSuccess(String email) {
                grabbedEmail.setText(email);
            }

            @Override
            public void onE(String errorMessage) {
                grabbedEmail.setText("Error");
            }
        });
        String preprompt = "You are speaking as our user, on behalf of them. They are You will to scrape the TOS of the company they are sending the request to,and must be as concise with the information as possible.";
        ReadData readData = new ReadData();
        preprompt = preprompt + "The users name, Region and DOB is" + readData.returnData(this, "userData.txt");
        TextView generationBox = findViewById(R.id.GenerationBox);
        ChatGenCall chatGenCall = new ChatGenCall();
        chatGenCall.generateRequest(this, preprompt, new ChatGenCall.generateRequestCallBack() {
            @Override
            public void onSuccess(String request) {
                generationBox.setText(request);
            }

            @Override
            public void onE(String errorMessage) {
                generationBox.setText("Error");
            }
        });
    }
}
