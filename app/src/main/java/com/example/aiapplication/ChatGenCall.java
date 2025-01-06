package com.example.aiapplication;
import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class  ChatGenCall {

    public interface generateRequestCallBack {
        void onSuccess(String request);

        void onE(String errorMessage);
    }

    public void generateRequest(Context context, String preprompt, @Nullable List<Uri> fileContents, String userRequest, generateRequestCallBack callback) {
        List<ChatRequest.Message> messages = new ArrayList<>();
        messages.add(new ChatRequest.Message("user", userRequest));
        messages.add(new ChatRequest.Message("system", preprompt));

        if (fileContents != null && !fileContents.isEmpty()) {
            for (Uri content : fileContents) {
                messages.add(new ChatRequest.Message("system", "Attached File Content: " + content));
            }
        }

        ChatRequest chatRequest = new ChatRequest("gpt-3.5-turbo", messages);
        Call<ChatResponse> call = ChatGPTAPIService.getApi(context).getChatResponse(chatRequest);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ChatResponse> call, @NonNull Response<ChatResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String reply = response.body().getChoices().get(0).getMessage().getContent();
                    callback.onSuccess(reply);
                } else {
                    callback.onE("Error: Failed to get response from ChatGPT");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ChatResponse> call, @NonNull Throwable t) {
                callback.onE("Error: " + t.getMessage());
            }
        });
    }
}