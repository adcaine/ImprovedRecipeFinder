package com.caine.allan.improvedrecipefinder;

import android.support.v4.app.Fragment;

import com.caine.allan.improvedrecipefinder.data.Recipe;
import com.caine.allan.onefragmentactivity.SingleFragmentActivity;

public class ListActivity extends SingleFragmentActivity implements RecipeListFragment.Callbacks {

    private static final String TRANS_ACTION = "transition";

    @Override
    protected Fragment createFragment() {
        return new RecipeListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_master_detail;
    }

    @Override
    public void onRecipeSelected(Recipe recipe) {
        Fragment recipeDetail = WebSiteFragment.newInstance(recipe != null ? recipe.getSourceURL() :
                getString(R.string.about_blank));
        int replacement = isTablet() ? R.id.detail_fragment_container : R.id.fragment_container;

        if(isTablet()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(replacement, recipeDetail)
                    .commit();
        }else{
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(replacement, recipeDetail)
                    .addToBackStack(TRANS_ACTION)
                    .commit();
        }
    }

    @Override
    public void onDetailRefresh(Recipe recipe) {
        if(isTablet()){
            onRecipeSelected(recipe);
        }
    }

    @Override
    public boolean isTablet(){
        return findViewById(R.id.detail_fragment_container) != null;
    }
}
