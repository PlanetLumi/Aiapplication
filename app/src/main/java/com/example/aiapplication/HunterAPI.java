package com.example.aiapplication;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HunterAPI {
    private static final String KeyURL = "https://api.hunter.io/v2/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(KeyURL)
                    .addConverterFactory(GsonConverterFactory.create());
                    .build();
            }
        return retrofit;
    }
}
