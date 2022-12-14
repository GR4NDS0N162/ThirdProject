package com.example.wear;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.wear.widget.WearableLinearLayoutManager;
import androidx.wear.widget.WearableRecyclerView;

import com.example.wear.data.Recipe;
import com.example.wear.data.RecipeService;
import com.example.wear.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity {
    private ActivityMainBinding binding;
    private WearableRecyclerView mRecyclerView;
    private List<Recipe> mRecipesData = new ArrayList<>();
    private List<Recipe> mRecyclerData = new ArrayList<>();
    private WearableRecyclerView.Adapter mAdapter = new WearableRecyclerViewAdapter(mRecyclerData);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Создание RecyclerView
        mRecyclerView = binding.recyclerView;
        mRecyclerView.setLayoutManager(new WearableLinearLayoutManager(MainActivity.this));
        mRecyclerView.setCircularScrollingGestureEnabled(true);
        mRecyclerView.setEdgeItemsCenteringEnabled(true);

        //--------------- Получение данных с Api ---------------
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://raw.githubusercontent.com/Lpirskaya/JsonLab/master/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                RecipeService service = retrofit.create(RecipeService.class);
                Call<List<Recipe>> getTownsRequest = service.getTowns();

                getTownsRequest.enqueue(new Callback<List<Recipe>>() {
                    @Override
                    public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                        if (response.isSuccessful()) {
                            // Обработка ответа
                            Log.i("[onResponse] call = ", call.toString());
                            Log.i("[onResponse] response = ", response.toString());

                            mRecipesData = response.body();

                            // Добавление объектов в RecyclerView
                            if (mRecipesData.size() != 0) {
                                mRecyclerData.clear();
                                mRecyclerData.addAll(mRecipesData);
                                mAdapter = new WearableRecyclerViewAdapter(mRecyclerData);
                                mAdapter.notifyDataSetChanged();
                                mRecyclerView.setAdapter(mAdapter);
                            }
                            else {
                                Toast.makeText(MainActivity.this, "Список городов пуст", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            ResponseBody errorBody = response.errorBody();
                            Log.i("[onResponse] errorBody = ", errorBody.toString());
                            Toast.makeText(MainActivity.this, "Ошибка получения данных", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Recipe>> call, Throwable t) {
                        // Обработка ошибок
                        Log.i("[onFailure] t = ", t.toString());
                        Toast.makeText(MainActivity.this, "Ошибка получения данных", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        thread.start();
        //--------------- Получение данных с Api ---------------


    }
}