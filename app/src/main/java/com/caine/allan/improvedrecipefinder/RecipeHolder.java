package com.caine.allan.improvedrecipefinder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.caine.allan.improvedrecipefinder.data.Recipe;

/**
 * Created by allancaine on 2015-10-26.
 */
public class RecipeHolder extends RecyclerView.ViewHolder {

    private RecipeView mRecipeView;

    public RecipeHolder(View itemView) {
        super(itemView);
        mRecipeView = (RecipeView)itemView;
    }

    public void bindView(Recipe recipe){
        mRecipeView.setTitleTextView(recipe.getTitle());
    }
}
