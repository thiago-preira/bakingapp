package com.udacity.android.bakingapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.udacity.android.bakingapp.R;
import com.udacity.android.bakingapp.model.Action;

import java.util.List;

public class RecipeActionAdapter extends RecyclerView.Adapter<RecipeActionAdapter.RecipeActionViewHolder> {

    private final List<Action> mActions;
    private final Context mContext;
    private final RecipeActionOnClickListener mRecipeActionOnClickListener;

    public RecipeActionAdapter(List<Action> mActions, Context mContext,
                               RecipeActionOnClickListener recipeActionOnClickListener) {
        this.mActions = mActions;
        this.mContext = mContext;
        this.mRecipeActionOnClickListener = recipeActionOnClickListener;
    }

    @NonNull
    @Override
    public RecipeActionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recipe_action, viewGroup, false);
        return new RecipeActionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeActionViewHolder recipeActionViewHolder, int position) {
        Action action = mActions.get(position);
        recipeActionViewHolder.loadAction(action);
    }

    @Override
    public int getItemCount() {
        if (mActions == null)
            return 0;
        return mActions.size();
    }

    class RecipeActionViewHolder extends RecyclerView.ViewHolder {

        private final Button mActionButton;
        
        public RecipeActionViewHolder(@NonNull View itemView) {
            super(itemView);
            mActionButton = itemView.findViewById(R.id.btn_recipe_action);
            if(mRecipeActionOnClickListener!=null){
                mActionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mRecipeActionOnClickListener.onRecipeActionClick(itemView,getAdapterPosition());
                    }
                });
            }
        }
        
        public void loadAction(Action action){
            mActionButton.setText(action.getDescription());
        }
    }

    public interface RecipeActionOnClickListener {
        void onRecipeActionClick(View view, int position);
    }

    public Action getAction(int position){
        return mActions.get(position);
    }
}
