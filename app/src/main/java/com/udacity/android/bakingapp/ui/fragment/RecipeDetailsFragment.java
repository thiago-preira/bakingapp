package com.udacity.android.bakingapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.android.bakingapp.R;
import com.udacity.android.bakingapp.model.Action;
import com.udacity.android.bakingapp.model.Recipe;
import com.udacity.android.bakingapp.ui.activity.RecipeDetailActivity;
import com.udacity.android.bakingapp.ui.adapter.RecipeActionAdapter;


import java.util.List;

import static com.udacity.android.bakingapp.utils.BakingAppConstants.RECIPE_KEY;

public class RecipeDetailsFragment extends BaseFragment {

    private Recipe mRecipe;
    private RecipeActionAdapter mRecipeActionsAdapter;


    private RecyclerView mActionRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        mRecipe = getArguments().getParcelable(RECIPE_KEY);

        setRecyclerView(view);

        return view;
    }

    private void setRecyclerView(View view) {
        mActionRecyclerView = view.findViewById(R.id.rv_recipe_steps);
        mActionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mActionRecyclerView.setHasFixedSize(true);
        setAdapter();
    }

    private void setAdapter() {
        List<Action> actions = mRecipe.getActions();
        mRecipeActionsAdapter = new RecipeActionAdapter(actions, getContext(), onRecipeActionClick());
        mActionRecyclerView.setAdapter(mRecipeActionsAdapter);
    }

    private RecipeActionAdapter.RecipeActionOnClickListener onRecipeActionClick() {
        return new RecipeActionAdapter.RecipeActionOnClickListener() {
            @Override
            public void onRecipeActionClick(View view, int position) {
                Action action = mRecipeActionsAdapter.getAction(position);
                RecipeDetailActivity recipeDetailActivity = (RecipeDetailActivity) getActivity();
                recipeDetailActivity.select(action);
                toast(action.getDescription());
            }
        };
    }

}
