package com.udacity.android.bakingapp.ui.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.udacity.android.bakingapp.data.RecipesRepository;

public class MainActivityViewModelFactory  extends ViewModelProvider.NewInstanceFactory {

    private final RecipesRepository mRepository;

    public MainActivityViewModelFactory(RecipesRepository repository) {
        this.mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MainActivityViewModel(mRepository);
    }
}
