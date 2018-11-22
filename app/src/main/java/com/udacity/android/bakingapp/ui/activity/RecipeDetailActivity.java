package com.udacity.android.bakingapp.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.udacity.android.bakingapp.R;
import com.udacity.android.bakingapp.model.Action;
import com.udacity.android.bakingapp.model.Recipe;
import com.udacity.android.bakingapp.ui.fragment.RecipeDetailsFragment;
import com.udacity.android.bakingapp.ui.fragment.RecipeInstructionsFragment;

import org.parceler.Parcels;

import static com.udacity.android.bakingapp.utils.BakingAppConstants.RECIPE_ACTIONS_SIZE;
import static com.udacity.android.bakingapp.utils.BakingAppConstants.RECIPE_KEY;
import static com.udacity.android.bakingapp.utils.BakingAppConstants.RECIPE_SELECTED_ACTION_KEY;

public class RecipeDetailActivity extends BaseActivity {

    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        mRecipe = Parcels.unwrap(getIntent().getParcelableExtra(RECIPE_KEY));
        getSupportActionBar().setTitle(mRecipe.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction tx = fragmentManager.beginTransaction();

        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        fragment.setArguments(getIntent().getExtras());
        tx.replace(R.id.frame_master, fragment);

        if (isLandscapeMode()) {
            RecipeInstructionsFragment instructionsFragment = new RecipeInstructionsFragment();
            instructionsFragment.setArguments(getIntent().getExtras());
            tx.replace(R.id.frame_detail, instructionsFragment);
        }

        tx.commit();

    }


    public void select(int position) {
        FragmentManager manager = getSupportFragmentManager();
        if (!isLandscapeMode()) {
            FragmentTransaction tx = manager.beginTransaction();
            RecipeInstructionsFragment instructionsFragment = new RecipeInstructionsFragment();
            Bundle bundle = new Bundle();
                bundle.putParcelable(RECIPE_KEY,Parcels.wrap(mRecipe));
                bundle.putInt(RECIPE_SELECTED_ACTION_KEY, position);
            instructionsFragment.setArguments(bundle);
            tx.replace(R.id.frame_master, instructionsFragment);
            tx.addToBackStack(null);
            tx.commit();
        } else {
            RecipeInstructionsFragment instructionsFragment =
                    (RecipeInstructionsFragment) manager.findFragmentById(R.id.frame_detail);
            instructionsFragment.setRecipe(mRecipe.getId(),position);
        }
    }
}
