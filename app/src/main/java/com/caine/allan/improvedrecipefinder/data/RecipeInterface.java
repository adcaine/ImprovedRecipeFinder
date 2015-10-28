package com.caine.allan.improvedrecipefinder.data;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by allancaine on 2015-10-26.
 */
public interface RecipeInterface {
    @GET("/search")
    Observable<RecipeSearchResponse> recipeSearch(@Query("q") String searchString);
}
