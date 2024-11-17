package com.example.aiapplication;

import android.content.Context;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import androidx.appcompat.app.AppCompatActivity;

public class HunterGenCall {
    String domain = "HyperXgaming.com";
    String apiKey = "d52f75345524c888b7a60263cd7fd9de6ca7dbe5";
    String department = "support";
    public interface EmailResultCallBack{
        void onSuccess(String email);
        void onE(String errorMessage);
    }
    public void fetchEmail(Context context, EmailResultCallBack callback){
        HunterApiService hunterApiService = HunterAPI.getClient().create(HunterApiService.class);
        Call<DomainSearchResponse> call = hunterApiService.getDomainSearch(domain, apiKey, department);
        call.enqueue(new Callback<DomainSearchResponse>() {
            @Override
            public void onResponse(Call<DomainSearchResponse> call, Response<DomainSearchResponse> response) {
            if (response.isSuccessful()) {
                DomainSearchResponse domainSearchResponse = response.body();
                String email = domainSearchResponse.getData().getEmails().get(0).getValue();
                callback.onSuccess(email);
            } else {
                callback.onE("Error");
            }
        };
            @Override
            public void onFailure(Call<DomainSearchResponse> call, Throwable t) {
                callback.onE("Network Failure: " + t.getMessage());
            }
        });
    }
}