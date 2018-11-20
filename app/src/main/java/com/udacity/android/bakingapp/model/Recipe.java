package com.udacity.android.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import static com.udacity.android.bakingapp.utils.BakingAppConstants.RECIPE_INGREDIENTS_KEY;

public class Recipe implements Parcelable {

    private long id;
    private List<Ingredient> ingredients;
    private List<Step> steps;
    private int servings;
    private String name;
    private String image;



    public Recipe(long id, List<Ingredient> ingredients, List<Step> steps, int servings, String name, String image) {
        this.id = id;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.name = name;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Step> getSteps() {
        return steps;
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

    public List<Action> getActions() {
        List<Action> actions = new ArrayList<>();

        Action ingredients = new Action("INGREDIENTS",  RECIPE_INGREDIENTS_KEY);
        actions.add(ingredients);

        for (Step step : this.steps) {
            Action stepAction = new Action(step.getShortDescription(), step.getId());
            actions.add(stepAction);
        }

        return actions;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeList(this.ingredients);
        dest.writeList(this.steps);
        dest.writeInt(this.servings);
        dest.writeString(this.name);
        dest.writeString(this.image);
    }

    public Recipe() {
    }

    protected Recipe(Parcel in) {
        this.id = in.readLong();
        this.ingredients = new ArrayList<Ingredient>();
        in.readList(this.ingredients, Ingredient.class.getClassLoader());
        this.steps = new ArrayList<Step>();
        in.readList(this.steps, Step.class.getClassLoader());
        this.servings = in.readInt();
        this.name = in.readString();
        this.image = in.readString();
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getIngredientsAsText(){
        if(ingredients==null){
            return "";
        }

        StringBuilder builder = new StringBuilder();
        for (Ingredient ingredient: ingredients) {
            String text = String.format("%s %s - %s \n",
                    String.valueOf(ingredient.getQuantity()),
                    ingredient.getMeasure(),
                    ingredient.getIngredient()
            );
            builder.append(text);
        }
        return builder.toString();
    }

}
