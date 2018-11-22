package com.udacity.android.bakingapp.model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import org.parceler.Parcel;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Parcel
@Entity(tableName = "step",
        foreignKeys = @ForeignKey(entity = Recipe.class
                , parentColumns = "id",
                childColumns = "recipeId",
                onDelete = CASCADE))
public class Step {

    @PrimaryKey(autoGenerate = true)
    private long stepId;
    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;
    private long recipeId;
    private boolean lastStep;

    public Step(long stepId, int id, String shortDescription, String description, String videoURL, String thumbnailURL, long recipeId, boolean lastStep) {
        this.stepId = stepId;
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
        this.recipeId = recipeId;
        this.lastStep = lastStep;
    }

    public int getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public long getRecipeId() {
        return recipeId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public void setRecipeId(long recipeId) {
        this.recipeId = recipeId;
    }

    public long getStepId() {
        return stepId;
    }

    public void setStepId(long stepId) {
        this.stepId = stepId;
    }


    public boolean isLastStep() {
        return lastStep;
    }

    public void setLastStep(boolean lastStep) {
        this.lastStep = lastStep;
    }

    @Ignore
    public Step() {
    }


}
