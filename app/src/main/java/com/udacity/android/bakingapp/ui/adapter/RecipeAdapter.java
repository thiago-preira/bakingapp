package com.udacity.android.bakingapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.android.bakingapp.R;
import com.udacity.android.bakingapp.model.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private static final String TAG = RecipeAdapter.class.getSimpleName();

    private final List<Recipe> mRecipes;
    private final Context mContext;
    private final RecipeOnClickListener mRecipeOnClickListener;


    public RecipeAdapter(List<Recipe> mRecipes, Context context, RecipeOnClickListener recipeOnClickListener) {
        this.mRecipes = mRecipes;
        this.mContext = context;
        this.mRecipeOnClickListener = recipeOnClickListener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecipeViewHolder recipeViewHolder, final int position) {
        Recipe recipe = mRecipes.get(position);
        recipeViewHolder.loadRecipe(recipe,mContext);
    }

    @Override
    public int getItemCount() {
        if (mRecipes == null) return 0;
        return mRecipes.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        private final ImageView mRecipeImage;
        private final TextView mRecipeName;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            mRecipeImage = itemView.findViewById(R.id.item_recipe_image);
            mRecipeName = itemView.findViewById(R.id.item_recipe_name);
            if (mRecipeOnClickListener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mRecipeOnClickListener.onRecipeClick(itemView,getAdapterPosition());
                    }
                });
            }
        }
        public void loadRecipe(Recipe recipe, Context context) {
            mRecipeName.setText(recipe.getName());
            loadImage(recipe.getImage(), context);
        }

        private void loadImage(String imageUrl, Context context) {
            if (TextUtils.isEmpty(imageUrl)) {
                return;
            }

            Picasso
                    .with(context)
                    .load(imageUrl)
                    .into(mRecipeImage, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            mRecipeImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        }

                        @Override
                        public void onError() {
                            Log.e(TAG, "Error loading Image: " + imageUrl);
                        }
                    });
        }

    }

    public interface RecipeOnClickListener {
        void onRecipeClick(View view, int position);
    }
}
