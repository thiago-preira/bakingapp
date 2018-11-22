package com.udacity.android.bakingapp.data.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.udacity.android.bakingapp.model.Step;

import java.util.List;

@Dao
public interface StepDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(List<Step> steps);

    @Query("SELECT * FROM step WHERE recipeId=:recipeId order by id")
    LiveData<List<Step>> loadStepsFromRecipe(long recipeId);

    @Query("SELECT * FROM step WHERE recipeId=:recipeId and id=:id")
    LiveData<Step> loadStepFromRecipe(int id, long recipeId);
}
