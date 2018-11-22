package com.udacity.android.bakingapp.ui.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import com.udacity.android.bakingapp.model.Ingredient;
import com.udacity.android.bakingapp.model.Recipe;
import com.udacity.android.bakingapp.model.Step;
import com.udacity.android.bakingapp.ui.activity.RecipeDetailActivity;
import com.udacity.android.bakingapp.ui.adapter.RecipeActionAdapter;
import com.udacity.android.bakingapp.ui.viewmodel.RecipeSharedViewModel;
import com.udacity.android.bakingapp.ui.viewmodel.RecipeSharedViewModelFactory;
import com.udacity.android.bakingapp.utils.InjectorUtils;


import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import static com.udacity.android.bakingapp.utils.BakingAppConstants.RECIPE_INGREDIENTS_KEY;
import static com.udacity.android.bakingapp.utils.BakingAppConstants.RECIPE_KEY;

public class RecipeDetailsFragment extends BaseFragment {

    private Recipe mRecipe;
    private RecipeActionAdapter mRecipeActionsAdapter;
    private RecipeSharedViewModel mViewModel;
    private List<Step> mSteps;


    private RecyclerView mActionRecyclerView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecipeSharedViewModelFactory factory = InjectorUtils.provideRecipeSharedViewModelFactory(getContext());
        mViewModel =  ViewModelProviders.of(getActivity(),factory).get(RecipeSharedViewModel.class);
        mRecipe = Parcels.unwrap(getArguments().getParcelable(RECIPE_KEY));
        mViewModel.getSteps(mRecipe.getId()).observe(getViewLifecycleOwner(), new Observer<List<Step>>() {
            @Override
            public void onChanged(@Nullable List<Step> steps) {
                mSteps = steps;
                setAdapter();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        setRecyclerView(view);
        return view;
    }

    private void setRecyclerView(View view) {
        mActionRecyclerView = view.findViewById(R.id.rv_recipe_steps);
        mActionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mActionRecyclerView.setHasFixedSize(true);
    }

    private void setAdapter() {
        List<Action> actions = getActions();
        mRecipeActionsAdapter = new RecipeActionAdapter(actions, getContext(), onRecipeActionClick());
        mActionRecyclerView.setAdapter(mRecipeActionsAdapter);
    }

    private RecipeActionAdapter.RecipeActionOnClickListener onRecipeActionClick() {
        return new RecipeActionAdapter.RecipeActionOnClickListener() {
            @Override
            public void onRecipeActionClick(View view, int position) {
                Action action = mRecipeActionsAdapter.getAction(position);
                RecipeDetailActivity recipeDetailActivity = (RecipeDetailActivity) getActivity();
                recipeDetailActivity.select(action.getPosition());
                toast(action.getDescription());
            }
        };
    }

    private List<Action> getActions(){
        List<Action> actions = new ArrayList<>();

        Action ingredients = new Action("INGREDIENTS",  RECIPE_INGREDIENTS_KEY);
        actions.add(ingredients);

        for (Step step :mSteps) {
            Action stepAction = new Action(step.getShortDescription(), step.getId());
            actions.add(stepAction);
        }

        return actions;
    }

}
