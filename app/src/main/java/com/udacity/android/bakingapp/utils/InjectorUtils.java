package com.udacity.android.bakingapp.utils;

import android.content.Context;

import com.udacity.android.bakingapp.data.RecipesRepository;
import com.udacity.android.bakingapp.data.database.AppDatabase;
import com.udacity.android.bakingapp.data.network.RecipeNetworkDataSource;
import com.udacity.android.bakingapp.ui.viewmodel.MainActivityViewModelFactory;
import com.udacity.android.bakingapp.ui.viewmodel.RecipeSharedViewModelFactory;

public class InjectorUtils {

    public static RecipesRepository provideRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        RecipeNetworkDataSource networkDataSource =
                RecipeNetworkDataSource.getInstance(executors);
        return RecipesRepository.getInstance(networkDataSource,
                database.recipeDao(),database.stepDao(),database.ingredientDao(),executors);
    }

    public static MainActivityViewModelFactory provideMainActivityViewModelFactory(Context context) {
        RecipesRepository repository = provideRepository(context);
        return new MainActivityViewModelFactory(repository);
    }

    public static RecipeSharedViewModelFactory provideRecipeSharedViewModelFactory(Context context) {
        RecipesRepository repository = provideRepository(context);
        return new RecipeSharedViewModelFactory(repository);
    }

}
