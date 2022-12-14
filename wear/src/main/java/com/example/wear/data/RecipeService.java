package com.example.wear.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeService {
    @GET("recipes2022.json")
    Call<List<Recipe>> getTowns();
}
