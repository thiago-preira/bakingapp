package com.udacity.android.bakingapp.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import org.parceler.Parcel;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Parcel
@Entity(tableName = "ingredient",
        foreignKeys = @ForeignKey(entity = Recipe.class,
                parentColumns = "id",
                childColumns = "recipeId",
                onDelete = CASCADE)
)
public class Ingredient {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private double quantity;
    private String measure;
    private String ingredient;
    private long recipeId;

    public Ingredient(long id, double quantity, String measure, String ingredient, long recipeId) {
        this.id = id;
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
        this.recipeId = recipeId;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public long getRecipeId() {
        return recipeId;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public void setRecipeId(long recipeId) {
        this.recipeId = recipeId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Ignore
    public Ingredient() {
    }
}
