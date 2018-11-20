package com.udacity.android.bakingapp.data.network;

import com.udacity.android.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BakingAPI {

    @GET("baking.json")
    Call<List<Recipe>> getRecipes();
}
