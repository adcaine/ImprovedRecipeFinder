package com.caine.allan.improvedrecipefinder.data;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by allancaine on 2015-10-26.
 */
public interface RecipeInterface {
    @GET("/search")
    void recipeSearch(@Query("q") String searchString, Callback<RecipeSearchResponse> callback);
}
