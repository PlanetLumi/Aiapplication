package com.example.aiapplication;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import okhttp3.ResponseBody;

public interface ChatGPTAPI {
    @Headers("Content-Type: application/json")
    @POST("v1/chat/completions")
    Call<ChatResponse> getChatResponse(@Body ChatRequest chatRequest);
}
