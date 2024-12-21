package com.example.aiapplication;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class ChatGPTAPIService {
    private static final String BASE_URL = "https://api.openai.com";
    private static final String API_KEY = "your name";
    private static Retrofit retrofit;

    public static Retrofit getInstance(){
        if (retrofit == null){
            OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request newRequest = chain.request().newBuilder()
                            .addHeader("Authorization", "Bearer " + API_KEY)
                            .build();
                    return chain.proceed(newRequest);
                }).build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    public static ChatGPTAPI getApi(){
        return getInstance().create(ChatGPTAPI.class);
    }
}
