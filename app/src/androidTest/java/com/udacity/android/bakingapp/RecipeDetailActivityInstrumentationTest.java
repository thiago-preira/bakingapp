package com.udacity.android.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.udacity.android.bakingapp.model.Ingredient;
import com.udacity.android.bakingapp.model.Recipe;
import com.udacity.android.bakingapp.model.Step;
import com.udacity.android.bakingapp.ui.activity.MainActivity;
import com.udacity.android.bakingapp.ui.activity.RecipeDetailActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class RecipeDetailActivityInstrumentationTest {

    Ingredient i = new Ingredient(1,"CUP","Milk");
    Step s = new Step(1,"Drop milk","Drop Milk in a mug","video","");
    Recipe recipe = new Recipe(1, Arrays.asList(i),Arrays.asList(s),1,"Milk Mug","");

    @Rule
    public ActivityTestRule<RecipeDetailActivity> activityTestRule =
            new ActivityTestRule<RecipeDetailActivity>(RecipeDetailActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
                    Intent result = new Intent(targetContext, RecipeDetailActivity.class);

                    result.putExtra("recipe", recipe);
                    return result;
                }
            };

    @Test
    public void checkClickOnStep() {
        // verify the visibility of recycler view on screen
        onView(withId(R.id.rv_recipe_steps))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.cv_container_ingredients))
                .check(matches(isDisplayed()));
    }

}
