package com.example.aiapplication;

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
    String domain  = "TooGoodToGo.com";
    String apiKey = "d52f75345524c888b7a60263cd7fd9de6ca7dbe5";
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.generation_area);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.generationArea), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        HunterApiService hunterApiService = HunterAPI.getClient().create(HunterApiService.class);
        Call<DomainSearchResponse> call = hunterApiService.getDomainSearch(domain, apiKey);
        call.enqueue(new Callback<DomainSearchResponse>() {
            final TextView grabbedEmail = findViewById(R.id.grabbedEmail);
            @Override
            public void onResponse(Call<DomainSearchResponse> call, Response<DomainSearchResponse> response) {
                if (response.isSuccessful()) {
                    DomainSearchResponse domainSearchResponse = response.body();
                    grabbedEmail.setText(domainSearchResponse.getData().getEmails().get(0).getValue());
                } else {
                    grabbedEmail.setText("Error");
                }
            }
            @Override
            public void onFailure(Call<DomainSearchResponse> call, Throwable t) {
                grabbedEmail.setText("Error");
            }
        });
    }
}
