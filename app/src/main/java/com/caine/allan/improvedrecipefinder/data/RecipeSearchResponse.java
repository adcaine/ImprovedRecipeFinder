package com.caine.allan.improvedrecipefinder.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by allancaine on 2015-10-26.
 */
public class RecipeSearchResponse {
    @SerializedName("recipes") private List<Recipe> mRecipes;

    public List<Recipe> getRecipes() {
        return mRecipes;
    }
}
