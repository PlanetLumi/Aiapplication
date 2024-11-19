package com.example.aiapplication;

import static android.text.TextUtils.replace;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
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
        String[] requestSplit = ReadData.returnData(GenerationArea.this,"userRequestInput.txt").split(",");
        HunterGenCall hunterGenCall = new HunterGenCall();
        hunterGenCall.fetchEmail(this, requestSplit[0].replace("[",""), new HunterGenCall.EmailResultCallBack() {
            @Override
            public void onSuccess(String email) {
                grabbedEmail.setText(email);
            }

            @Override
            public void onE(String errorMessage) {
                grabbedEmail.setText("Error");
            }
        });
        String preprompt = prePromptArea.prePromptRefund(GenerationArea.this);
        TextView generationBox = findViewById(R.id.GenerationBox);
        ChatGenCall chatGenCall = new ChatGenCall();
        chatGenCall.generateRequest(this, preprompt,requestSplit[1].replace("]" , ""), new ChatGenCall.generateRequestCallBack() {
            @Override
            public void onSuccess(String request) {
                generationBox.setText(request);
            }

            @Override
            public void onE(String errorMessage) {
                generationBox.setText("Error");
            }
        });
        Button sendEmail = this.findViewById(R.id.sendEmail);
        sendEmail.setOnClickListener(v -> {
                     emailFunc.sendEmail(grabbedEmail.getText().toString(), "Request", generationBox.getText().toString());
                });
        ExitButtonFunc.exitBtn(GenerationArea.this, MainActivity.class);
    }
}
