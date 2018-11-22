package com.udacity.android.bakingapp.ui.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.udacity.android.bakingapp.data.RecipesRepository;

public class RecipeSharedViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final RecipesRepository mRepository;

    public RecipeSharedViewModelFactory(RecipesRepository repository) {
        this.mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new RecipeSharedViewModel(mRepository);
    }
}
