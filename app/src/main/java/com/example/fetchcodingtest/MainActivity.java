package com.example.fetchcodingtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Item> itemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        itemsList = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fetch-hiring.s3.amazonaws.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FetchAPI fetchAPI = retrofit.create(FetchAPI.class);

        Call<List<Item>> call = fetchAPI.getItems();

        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(@NonNull Call<List<Item>> call, @NonNull Response<List<Item>> response) {
                if (response.code() != 200) {
                    return;
                }
                List<Item> items = response.body();
                assert items != null;
                itemsList.addAll(items);
                removeEmptyNames(itemsList);
                removeNullNames(itemsList);
                sort(itemsList);
                populateRecyclerView(itemsList);
            }

            @Override
            public void onFailure(@NonNull Call<List<Item>> call, @NonNull Throwable t) {
            }
        });
    }

    private void sort(List<Item> itemsList) {
        itemsList.sort(Comparator.comparing(Item::getListIdAsInt).thenComparing(Item::getIdAsInt));
    }

    private void removeEmptyNames(List<Item> itemsList) {
        itemsList.removeIf(item -> item.getName() != null && item.getName().isEmpty());
    }

    private void removeNullNames(List<Item> itemsList) {
        itemsList.removeIf(item -> item.getName() == null);
    }

    private void populateRecyclerView(List<Item> itemsList) {
        ItemAdapter adapter = new ItemAdapter(this, itemsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}