package com.udacity.android.bakingapp.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Log;

import com.udacity.android.bakingapp.data.database.dao.IngredientDao;
import com.udacity.android.bakingapp.data.database.dao.RecipeDao;
import com.udacity.android.bakingapp.data.database.dao.StepDao;
import com.udacity.android.bakingapp.data.network.RecipeNetworkDataSource;
import com.udacity.android.bakingapp.model.Ingredient;
import com.udacity.android.bakingapp.model.Recipe;
import com.udacity.android.bakingapp.model.Step;
import com.udacity.android.bakingapp.utils.AppExecutors;

import java.util.List;

public class RecipesRepository {
    private static final String TAG = RecipesRepository.class.getSimpleName();

    private static final Object LOCK = new Object();
    private static RecipesRepository sInstance;
    private final RecipeNetworkDataSource mNetworkDataSource;
    private final RecipeDao mRecipeDao;
    private final StepDao mStepDao;
    private final IngredientDao mIngredientDao;
    private final AppExecutors mExecutor;

    public RecipesRepository(RecipeNetworkDataSource recipeNetworkDataSource,
                             RecipeDao recipeDao,
                             StepDao stepDao,
                             IngredientDao ingredientDao,
                             AppExecutors executor) {

        this.mNetworkDataSource = recipeNetworkDataSource;
        this.mRecipeDao = recipeDao;
        this.mStepDao = stepDao;
        this.mIngredientDao = ingredientDao;
        this.mExecutor = executor;
        loadRecipes();
    }

    private void loadRecipes() {
        LiveData<List<Recipe>> recipesFromNetwork = mNetworkDataSource.getRecipes();
        recipesFromNetwork.observeForever(new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                mExecutor.diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mRecipeDao.bulkInsert(recipes);
                        for (Recipe recipe: recipes) {
                            for (Step step:  recipe.getSteps() ) {
                                step.setRecipeId(recipe.getId());
                                if(step.getId()==recipe.getSteps().size()-1){
                                    step.setLastStep(true);
                                }
                            }
                            mStepDao.bulkInsert(recipe.getSteps());
                            for(Ingredient ingredient: recipe.getIngredients()){
                                ingredient.setRecipeId(recipe.getId());
                            }
                            mIngredientDao.bulkInsert(recipe.getIngredients());
                        }
                    }
                });
            }
        });
    }

    public static RecipesRepository getInstance(RecipeNetworkDataSource networkDataSource,
                                                RecipeDao recipeDao,
                                                StepDao stepDao,
                                                IngredientDao ingredientDao,
                                                AppExecutors executors) {

        Log.d(TAG, "Getting the repository");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new RecipesRepository(networkDataSource,
                        recipeDao,
                        stepDao,
                        ingredientDao,
                        executors);
                Log.d(TAG, "Made new repository");
            }
        }
        return sInstance;

    }

    public LiveData<List<Recipe>> getRecipes() {
        return mRecipeDao.loadRecipes();
    }

    public LiveData<List<Step>> getSteps(long recipeId){
        return mStepDao.loadStepsFromRecipe(recipeId);
    }

    public LiveData<List<Ingredient>> getIngredients(long recipeId){
        return mIngredientDao.loadIngredientFromRecipe(recipeId);
    }

    public LiveData<Step> loadStepFromRecipe(int id,long recipeId){
        return mStepDao.loadStepFromRecipe(id,recipeId);
    }
}
