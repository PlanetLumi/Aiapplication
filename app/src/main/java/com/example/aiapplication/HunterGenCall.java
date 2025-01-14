package com.example.aiapplication;

import android.content.Context;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HunterGenCall {
    String department = "support"; //Always going to use support
    String type = "generic"; //Never used to find specific workers email`
    public interface EmailResultCallBack{
        void onSuccess(String email);
        void onE(String errorMessage);
    }
    public void fetchEmail(Context context,  String domain, EmailResultCallBack callback){
        //finds saved key from intents
        String apiKey = saveUserID.grabKey(context, "hunterIoKey");
        //initialise API
        HunterApiService hunterApiService = HunterAPI.getClient().create(HunterApiService.class);
        //standardise input
        domain = (domain.replace("www.", "")).replace("http://", "").trim();
        //checks if domain is valid
        if(!domain.contains(".com") && !domain.contains(".net") && !domain.contains(".org") && !domain.contains(".co.uk") && !domain.contains(".co.za") && !domain.contains(".co.nz")){
                callback.onE("Not a valid domain");
                return;
        }
        //SET PARAMETERS FOR API REQUEST
        Call<DomainSearchResponse> call = hunterApiService.getDomainSearch(domain, apiKey, department, type);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<DomainSearchResponse> call, Response<DomainSearchResponse> response) {
                if (response.isSuccessful()) {
                    DomainSearchResponse domainSearchResponse = response.body();
                    if (domainSearchResponse.getData().getEmails().isEmpty()) {
                        callback.onE("No email found");
                    } else {
                        //Save email
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