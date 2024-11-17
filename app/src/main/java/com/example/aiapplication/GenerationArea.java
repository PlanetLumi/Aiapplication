package com.example.aiapplication;

import static android.text.TextUtils.replace;

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
        String preprompt = "You are speaking as our user. Please use first person. You will operate under the usual TOS standard of customer service, such as refunds,and must be as concise with the information as possible. Please build customer service email requests on our user behalf";
        ReadData readData = new ReadData();
        preprompt = preprompt + "The users data is" + readData.returnData(this, "userData.txt") + "please only give this information if deemed nessecary.";
        preprompt = preprompt + "The user is requesting a refund from" + requestSplit[0].replace("[", "");
        preprompt = preprompt + "Please use your understanding of the given company to accurately build a customer service request.";
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
        ExitButtonFunc.exitBtn(GenerationArea.this, MainActivity.class);
    }
}
