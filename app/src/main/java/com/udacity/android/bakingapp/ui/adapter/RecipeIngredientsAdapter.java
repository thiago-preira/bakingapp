package com.udacity.android.bakingapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.udacity.android.bakingapp.R;
import com.udacity.android.bakingapp.model.Ingredient;

import java.util.List;

public class RecipeIngredientsAdapter extends BaseAdapter {

    private final List<Ingredient> mIngredients;
    private final Context context;

    public RecipeIngredientsAdapter(List<Ingredient> mIngredients, Context context) {
        this.mIngredients = mIngredients;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mIngredients.size();
    }

    @Override
    public Object getItem(int position) {
        return mIngredients.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mIngredients.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Ingredient ingredient = mIngredients.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item_recipe_ingredients, parent, false);
        }

        TextView ingredientText = view.findViewById(R.id.tv_recipe_ingredients_ingredient);
        ingredientText.setText(ingredient.getIngredient());

        TextView quantityText = view.findViewById(R.id.tv_recipe_ingredients_quantity);
        quantityText.setText(String.valueOf(ingredient.getQuantity()));

        TextView measureText = view.findViewById(R.id.tv_recipe_ingredients_measure);
        measureText.setText(ingredient.getMeasure());

        return view;
    }
}
