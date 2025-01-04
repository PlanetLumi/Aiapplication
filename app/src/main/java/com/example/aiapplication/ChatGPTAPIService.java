package com.example.aiapplication;

import android.content.Context;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatGPTAPIService {
    private static final String BASE_URL = "https://api.openai.com";
    private static Retrofit retrofit;

    // Initialize Retrofit instance
    public static Retrofit getInstance(Context context) {
        if (retrofit == null) {
            // Fetch the API key using the context
            String apiKey = saveUserID.grabKey(context, "chatGptKey");

            // Create OkHttpClient with the API key in the Authorization header
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        Request newRequest = chain.request().newBuilder()
                                .addHeader("Authorization", "Bearer " + apiKey)
                                .build();
                        return chain.proceed(newRequest);
                    }).build();

            // Build the Retrofit instance
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    // Create API interface
    public static ChatGPTAPI getApi(Context context) {
        return getInstance(context).create(ChatGPTAPI.class);
    }
}