package com.udacity.android.bakingapp.ui.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.udacity.android.bakingapp.data.RecipesRepository;
import com.udacity.android.bakingapp.model.Ingredient;
import com.udacity.android.bakingapp.model.Step;

import java.util.List;

public class RecipeSharedViewModel extends ViewModel {
    private final RecipesRepository mRepository;

    public RecipeSharedViewModel(RecipesRepository repository) {
        this.mRepository = repository;

    }

    public LiveData<List<Ingredient>> getIngredients(long recipeId) {
        return mRepository.getIngredients(recipeId);
    }

    public LiveData<List<Step>> getSteps(long recipeId) {
        return mRepository.getSteps(recipeId);
    }

    public LiveData<Step> loadStepFromRecipe(int id, long recipeId) {
        return mRepository.loadStepFromRecipe(id, recipeId);
    }

}
