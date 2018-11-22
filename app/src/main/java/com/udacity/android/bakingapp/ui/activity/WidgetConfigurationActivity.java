package com.udacity.android.bakingapp.ui.activity;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.udacity.android.bakingapp.BakingAppWidgetProvider;
import com.udacity.android.bakingapp.R;
import com.udacity.android.bakingapp.data.RecipesRepository;
import com.udacity.android.bakingapp.model.Ingredient;
import com.udacity.android.bakingapp.model.Recipe;
import com.udacity.android.bakingapp.utils.InjectorUtils;

import java.util.List;

public class WidgetConfigurationActivity extends BaseActivity {
    private static final String TAG = WidgetConfigurationActivity.class.getSimpleName();

    private ListView mListView;
    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private ArrayAdapter<Recipe> recipeArrayAdapter;
    private RecipesRepository mRecipesRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wigdet_configuration);
        setResult(RESULT_CANCELED);
        mListView = findViewById(R.id.lv_widget_recipes);
        mRecipesRepository = InjectorUtils.provideRepository(getContext());
        mRecipesRepository.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                recipeArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1);
                recipeArrayAdapter.addAll(recipes);
                mListView.setAdapter(recipeArrayAdapter);
            }
        });

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            appWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);

            if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
                finish();
            }
        }

        mListView.setOnItemClickListener(onRecipeClickListener());


    }

    private AdapterView.OnItemClickListener onRecipeClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = getApplicationContext();
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                Recipe recipe = recipeArrayAdapter.getItem(position);
                mRecipesRepository.getIngredients(recipe.getId())
                        .observe(WidgetConfigurationActivity.this, new Observer<List<Ingredient>>() {
                    @Override
                    public void onChanged(@Nullable List<Ingredient> ingredients) {
                        BakingAppWidgetProvider.updateAppWidget(getContext(),
                                appWidgetManager,
                                appWidgetId,
                                ingredients
                        );
                        Intent resultValue = new Intent();
                        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                        setResult(RESULT_OK, resultValue);
                        finish();

                    }
                });
            }
        };
    }
}
