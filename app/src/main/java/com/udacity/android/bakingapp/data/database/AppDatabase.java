package com.udacity.android.bakingapp.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.udacity.android.bakingapp.data.database.dao.IngredientDao;
import com.udacity.android.bakingapp.data.database.dao.RecipeDao;
import com.udacity.android.bakingapp.data.database.dao.StepDao;
import com.udacity.android.bakingapp.model.Ingredient;
import com.udacity.android.bakingapp.model.Recipe;
import com.udacity.android.bakingapp.model.Step;

@Database(entities = {Recipe.class, Step.class, Ingredient.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final String DATABASE_NAME = "recipes";
    private static final Object LOCK = new Object();
    private static AppDatabase sInstance;


    public static AppDatabase getInstance(Context context) {
        Log.d(LOG_TAG, "Getting the database");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME).build();
                Log.d(LOG_TAG, "Made new database");
            }
        }
        return sInstance;
    }

    public abstract RecipeDao recipeDao();

    public abstract IngredientDao ingredientDao();

    public abstract StepDao stepDao();

}
