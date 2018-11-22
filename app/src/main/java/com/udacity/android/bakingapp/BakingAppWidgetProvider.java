package com.udacity.android.bakingapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import com.udacity.android.bakingapp.data.RecipesRepository;
import com.udacity.android.bakingapp.model.Ingredient;
import com.udacity.android.bakingapp.model.Recipe;
import com.udacity.android.bakingapp.utils.InjectorUtils;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidgetProvider extends AppWidgetProvider {

    private static List<Ingredient> mIngredients;

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int appWidgetId, List<Ingredient> ingredients) {
        mIngredients = ingredients;
        String widgetText = "Falhou";
        if (mIngredients != null) {
            widgetText = getIngredientsAsText();
        }
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static String getIngredientsAsText() {
        StringBuffer buffer = new StringBuffer();
        for (Ingredient ingredient : mIngredients) {
            String format = String.format("%.2f %s - %s",
                    ingredient.getQuantity(),
                    ingredient.getMeasure(),
                    ingredient.getIngredient());
            buffer.append(format + "\n");
        }

        return buffer.toString();
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

}

