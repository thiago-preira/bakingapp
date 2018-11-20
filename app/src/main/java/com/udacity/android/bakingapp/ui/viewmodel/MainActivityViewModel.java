package com.udacity.android.bakingapp.ui.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.udacity.android.bakingapp.data.RecipesRepository;
import com.udacity.android.bakingapp.model.Recipe;

import java.util.List;

public class MainActivityViewModel extends ViewModel {
    private final RecipesRepository mRepository;
    private final LiveData<List<Recipe>> mRecipes;

    public MainActivityViewModel(RecipesRepository recipesRepository) {
        this.mRepository = recipesRepository;
        this.mRecipes = mRepository.getRecipes();
    }

    public LiveData<List<Recipe>> getRecipes() {
        return mRecipes;
    }
}
