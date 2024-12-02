package com.example.aiapplication;

import android.content.Context;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HunterGenCall {
    String apiKey = "d52f75345524c888b7a60263cd7fd9de6ca7dbe5";
    String department = "support";
    String type = "generic";
    public interface EmailResultCallBack{
        void onSuccess(String email);
        void onE(String errorMessage);
    }
    public void fetchEmail(Context context,  String domain, EmailResultCallBack callback){
        HunterApiService hunterApiService = HunterAPI.getClient().create(HunterApiService.class);
        domain = (domain.replace("www.", "")).replace("http://", "").trim();
        if(!domain.contains(".com") && !domain.contains(".net") && !domain.contains(".org")){
                callback.onE("Not a valid domain");
                return;
        }
        Call<DomainSearchResponse> call = hunterApiService.getDomainSearch(domain, apiKey, department, type);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<DomainSearchResponse> call, Response<DomainSearchResponse> response) {
                if (response.isSuccessful()) {
                    DomainSearchResponse domainSearchResponse = response.body();
                    if (domainSearchResponse.getData().getEmails().isEmpty()) {
                        callback.onE("No email found");
                    } else {
                        String email = domainSearchResponse.getData().getEmails().get(0).getValue();
                        callback.onSuccess(email);
                    }
                } else {
                    setPopup.showError(context, "Error", "Could not grab email");
                    callback.onE("Could not grab");
                }
            }

            @Override
            public void onFailure(Call<DomainSearchResponse> call, Throwable t) {
                callback.onE("Network Failure: " + t.getMessage());
            }
        });
    }
}