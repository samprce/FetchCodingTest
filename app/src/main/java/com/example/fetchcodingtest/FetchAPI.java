package com.example.fetchcodingtest;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface FetchAPI {
    @GET("hiring.json")
    Call<List<Item>> getItems();
}
