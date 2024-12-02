package com.example.aiapplication;
import android.os.Bundle;
import android.widget.ImageButton;
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
        int layoutId = setPalette.setLayout(GenerationArea.this, "generation_area");
        setContentView(layoutId);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.generationArea), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String preprompt = findType();
        TextView grabbedEmail = findViewById(R.id.grabbedEmail);
        String[] requestSplit = ReadData.returnData(GenerationArea.this,"userRequestInput.txt").split(",");

        HunterGenCall hunterGenCall = new HunterGenCall();
        hunterGenCall.fetchEmail(this, requestSplit[1], new HunterGenCall.EmailResultCallBack() {
            @Override
            public void onSuccess(String email) {
                grabbedEmail.setText(email);
            }

            @Override
            public void onE(String errorMessage) {
                grabbedEmail.setText("Error");
                setPopup.showError(GenerationArea.this, "Email Grab Failed!", "Please try again.");
            }
        });

        TextView generationBox = findViewById(R.id.GenerationBox);
        generationBox.setFocusable(true);
        generationBox.setFocusableInTouchMode(true);
        ChatGenCall chatGenCall = new ChatGenCall();
        chatGenCall.generateRequest(preprompt,requestSplit[3].replace("]" , ""), new ChatGenCall.generateRequestCallBack() {
            @Override
            public void onSuccess(String request) { //Code has to be placed into this function because it takes too long to receive an answer comparatively
                generationBox.setText(request);
                ImageButton sendEmail = findViewById(R.id.sendEmail);
                String subject = findElements.findSubject(request);
                sendEmail.setOnClickListener(v -> {
                    emailFunc.sendEmail(GenerationArea.this,grabbedEmail.getText().toString(), subject, request.replace(subject, ""));
                });
                setPopup.showSuccess(GenerationArea.this, "successbot","Request sent!", null);
            }

            @Override
            public void onE(String errorMessage) {
                generationBox.setText("Error");
                setPopup.showError(GenerationArea.this, "Generation failed!", "Please try again.");
            }
        });

        ExitButtonFunc.exitBtn(GenerationArea.this, RequestPage.class);
    }

    private String findType() {
        String generationType = ReadData.returnData(GenerationArea.this, "userChoice.txt");
        if (generationType.equals("1")) {
            return prePromptArea.prePromptRefund(GenerationArea.this);
        } else {
            return prePromptArea.prePromptComplaints(GenerationArea.this);
    }
}
}
