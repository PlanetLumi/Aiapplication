package com.example.aiapplication;
import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.List;

public class ChatGenCall {

    public interface generateRequestCallBack{
        void onSuccess(String request);
        void onE(String errorMessage);
    }

    public void generateRequest(String preprompt, String userRequest, generateRequestCallBack callback){
        List<ChatRequest.Message> messages = new ArrayList<>();
        messages.add(new ChatRequest.Message("user", userRequest));
        messages.add(new ChatRequest.Message("system", preprompt));
        ChatRequest chatRequest = new ChatRequest("gpt-3.5-turbo", messages);
        Call<ChatResponse> call = ChatGPTAPIService.getApi().getChatResponse(chatRequest);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ChatResponse> call, @NonNull Response<ChatResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    String reply = response.body().getChoices().get(0).getMessage().getContent();
                    callback.onSuccess(reply);
                } else {
                    callback.onE("Error");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ChatResponse> call, @NonNull Throwable t) {
                String reply = "Error";
                callback.onE(reply);
            }
        });
    }
}