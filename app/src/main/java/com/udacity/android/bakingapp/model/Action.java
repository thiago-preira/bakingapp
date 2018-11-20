package com.udacity.android.bakingapp.model;

public class Action {
    private String description;
    private int position;

    public Action(String description, int position) {
        this.description = description;
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public int getPosition() {
        return position;
    }
}
