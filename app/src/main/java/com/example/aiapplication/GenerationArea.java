package com.example.aiapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

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
            public String onSuccess(String request) { //Code has to be placed into this function becuase it takes too long to receive an answer comparatively
                generationBox.setText(request);
                Button sendEmail = findViewById(R.id.sendEmail);
                String subject = findElements.findSubject(request);
                request = request.replace(subject, "");
                String finalRequest = request;
                sendEmail.setOnClickListener(v -> {
                    emailFunc.sendEmail(GenerationArea.this,grabbedEmail.getText().toString(), subject, finalRequest);
                });
                return request;
            }

            @Override
            public void onE(String errorMessage) {
                generationBox.setText("Error");
            }
        });

        ExitButtonFunc.exitBtn(GenerationArea.this, LoginPage.class);
    }
}
