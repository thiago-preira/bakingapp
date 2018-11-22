package com.udacity.android.bakingapp.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import org.parceler.Parcel;

import java.util.List;

@Parcel
@Entity(tableName = "recipe")
public class Recipe {

    @PrimaryKey
    private long id;
    private int servings;
    private String name;
    private String image;
    @Ignore
    private List<Ingredient> ingredients;
    @Ignore
    private List<Step> steps;

    public Recipe(long id, int servings, String name, String image) {
        this.id = id;
        this.servings = servings;
        this.name = name;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public int getServings() {
        return servings;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    @Ignore
    public Recipe() {
    }


}
