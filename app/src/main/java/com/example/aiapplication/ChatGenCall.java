package com.example.aiapplication;

import android.content.Context;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ChatGenCall {

    public interface generateRequestCallBack{
        void onSuccess(String request);
        void onE(String errorMessage);
    }

    public void generateRequest(Context context, String preprompt, String userRequest, generateRequestCallBack callback){
        List<ChatRequest.Message> messages = new ArrayList<>();
        messages.add(new ChatRequest.Message("user", userRequest));
        messages.add(new ChatRequest.Message("system", preprompt));
        ChatRequest chatRequest = new ChatRequest("gpt-3.5-turbo", messages);
        Call<ChatResponse> call = ChatGPTAPIService.getApi().getChatResponse(chatRequest);
        call.enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                if (response.isSuccessful()) {
                    String reply = response.body().getChoices().get(0).getMessage().getContent();
                    callback.onSuccess(reply);
                }else{
                    callback.onE("Error");
                    }
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                String reply = "Error";
                callback.onE(reply);
            }
        });
    }
}