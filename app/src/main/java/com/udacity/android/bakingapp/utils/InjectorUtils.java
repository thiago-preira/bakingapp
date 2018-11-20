package com.udacity.android.bakingapp.utils;

import android.content.Context;

import com.udacity.android.bakingapp.data.RecipesRepository;
import com.udacity.android.bakingapp.data.network.RecipeNetworkDataSource;
import com.udacity.android.bakingapp.ui.viewmodel.MainActivityViewModelFactory;

public class InjectorUtils {
    public static RecipeNetworkDataSource provideNetworkDataSource(Context context) {
        provideRepository(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        return RecipeNetworkDataSource.getInstance(context.getApplicationContext(), executors);
    }

    public static RecipesRepository provideRepository(Context context) {
        AppExecutors executors = AppExecutors.getInstance();
        RecipeNetworkDataSource networkDataSource =
                RecipeNetworkDataSource.getInstance(context.getApplicationContext(), executors);
        return RecipesRepository.getInstance(networkDataSource, executors);
    }

    public static MainActivityViewModelFactory provideMainActivityViewModelFactory(Context context) {
        RecipesRepository repository = provideRepository(context.getApplicationContext());
        return new MainActivityViewModelFactory(repository);
    }

}
