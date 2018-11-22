package com.udacity.android.bakingapp.data.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.udacity.android.bakingapp.model.Recipe;

import java.util.List;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM recipe")
    LiveData<List<Recipe>> loadRecipes();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(List<Recipe> recipes);

    @Query("SELECT * FROM recipe where id=:id")
    Recipe loadRecipe(long id);
}
