package com.caine.allan.improvedrecipefinder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.caine.allan.improvedrecipefinder.data.Recipe;

/**
 * Created by allancaine on 2015-10-26.
 */
public class RecipeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private RecipeView mRecipeView;

    public RecipeHolder(View itemView) {
        super(itemView);
        mRecipeView = (RecipeView)itemView;
        mRecipeView.setOnClickListener(this);
    }

    public void bindView(Recipe recipe){
        mRecipeView.setTitleTextView(recipe.getTitle());
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), mRecipeView.toString(), Toast.LENGTH_SHORT).show();
    }
}
