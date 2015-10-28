package com.caine.allan.improvedrecipefinder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.caine.allan.improvedrecipefinder.data.Recipe;

import java.util.List;


/**
 * Created by allancaine on 2015-10-26.
 */
public class RecipeListAdapter extends RecyclerView.Adapter<RecipeHolder> {

    private Context mContext;
    private List<Recipe> mRecipeList;

    public RecipeListAdapter(Context context, List<Recipe> recipeList) {
        mContext = context;
        mRecipeList = recipeList;
    }

    public void setRecipeList(List<Recipe> recipeList) {
        mRecipeList = recipeList;
    }

    @Override
    public RecipeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecipeView recipeView = new RecipeView(mContext);
        return new RecipeHolder(recipeView);
    }

    @Override
    public void onBindViewHolder(RecipeHolder holder, int position) {
        holder.bindView(mRecipeList.get(position));
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }
}
