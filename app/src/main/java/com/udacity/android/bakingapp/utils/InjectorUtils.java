package com.udacity.android.bakingapp.utils;

import com.udacity.android.bakingapp.data.RecipesRepository;
import com.udacity.android.bakingapp.data.network.RecipeNetworkDataSource;
import com.udacity.android.bakingapp.ui.viewmodel.MainActivityViewModelFactory;

public class InjectorUtils {

    public static RecipesRepository provideRepository() {
        AppExecutors executors = AppExecutors.getInstance();
        RecipeNetworkDataSource networkDataSource =
                RecipeNetworkDataSource.getInstance(executors);
        return RecipesRepository.getInstance(networkDataSource);
    }

    public static MainActivityViewModelFactory provideMainActivityViewModelFactory() {
        RecipesRepository repository = provideRepository();
        return new MainActivityViewModelFactory(repository);
    }

}
