package com.example.aiapplication;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HunterApiService {
   @GET("domain-search")
   Call<DomainSearchResponse> getDomainSearch(
           @Query("domain") String domain,
           @Query("api_key") String apiKey
   );
}