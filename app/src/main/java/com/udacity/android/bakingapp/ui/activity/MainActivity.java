package com.udacity.android.bakingapp.ui.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.udacity.android.bakingapp.R;
import com.udacity.android.bakingapp.model.Recipe;
import com.udacity.android.bakingapp.ui.adapter.RecipeAdapter;
import com.udacity.android.bakingapp.ui.viewmodel.MainActivityViewModel;
import com.udacity.android.bakingapp.ui.viewmodel.MainActivityViewModelFactory;
import com.udacity.android.bakingapp.utils.DeviceUtils;
import com.udacity.android.bakingapp.utils.InjectorUtils;

import java.util.List;

import static com.udacity.android.bakingapp.utils.BakingAppConstants.RECIPE_KEY;

public class MainActivity extends BaseActivity {

    private RecyclerView mRecipeRecyclerView;
    private List<Recipe> mRecipes;
    private MainActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRecyclerView();
        if(DeviceUtils.hasInternet(this)){
            setViewModel();
        }
    }

    private void setViewModel() {
        MainActivityViewModelFactory factory = InjectorUtils.provideMainActivityViewModelFactory();
        mViewModel = ViewModelProviders.of(this, factory).get(MainActivityViewModel.class);
        mViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                mRecipes = recipes;
                mRecipeRecyclerView.setAdapter(new RecipeAdapter(mRecipes, MainActivity.this, onRecipeClick()));
            }
        });
    }

    private void setRecyclerView() {
        mRecipeRecyclerView = findViewById(R.id.recycler_view_recipe);
        mRecipeRecyclerView.setHasFixedSize(true);
        setLayoutManager();
    }

    private void setLayoutManager() {
        if (isLandscapeMode()) {
            int mNoOfColumns = DeviceUtils.numberOfColumns(this);
            mRecipeRecyclerView.setLayoutManager(new GridLayoutManager(this, mNoOfColumns));
        } else {
            mRecipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    private RecipeAdapter.RecipeOnClickListener onRecipeClick() {
        return new RecipeAdapter.RecipeOnClickListener() {
            @Override
            public void onRecipeClick(View view, int position) {
                Recipe recipe = mRecipes.get(position);
                Intent recipeDetailIntent = new Intent(getContext(), RecipeDetailActivity.class);
                recipeDetailIntent.putExtra(RECIPE_KEY, recipe);
                startActivity(recipeDetailIntent);
            }
        };
    }
}
