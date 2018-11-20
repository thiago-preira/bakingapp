package com.udacity.android.bakingapp.data;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.udacity.android.bakingapp.data.network.RecipeNetworkDataSource;
import com.udacity.android.bakingapp.model.Recipe;
import com.udacity.android.bakingapp.utils.AppExecutors;

import java.util.List;

public class RecipesRepository {
    private static final String TAG = RecipesRepository.class.getSimpleName();

    private static final Object LOCK = new Object();
    private static RecipesRepository sInstance;
    private final RecipeNetworkDataSource mNetworkDataSource;
    private final LiveData<List<Recipe>> mRecipes;

    public RecipesRepository(RecipeNetworkDataSource recipeNetworkDataSource) {
        this.mNetworkDataSource = recipeNetworkDataSource;
        this.mRecipes = mNetworkDataSource.getRecipes();
    }

    public static RecipesRepository getInstance(RecipeNetworkDataSource networkDataSource) {
        Log.d(TAG, "Getting the repository");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new RecipesRepository(networkDataSource);
                Log.d(TAG, "Made new repository");
            }
        }
        return sInstance;

    }

    public LiveData<List<Recipe>> getRecipes(){
       return mRecipes;
    }
}
