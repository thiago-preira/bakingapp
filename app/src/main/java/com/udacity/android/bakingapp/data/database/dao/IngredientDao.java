package com.udacity.android.bakingapp.data.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.udacity.android.bakingapp.model.Ingredient;

import java.util.List;

@Dao
public interface IngredientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(List<Ingredient> ingredients);

    @Query("SELECT * FROM ingredient WHERE recipeId=:recipeId")
    LiveData<List<Ingredient>> loadIngredientFromRecipe(long recipeId);
}
