package com.caine.allan.improvedrecipefinder;

import android.support.v4.app.Fragment;

import com.caine.allan.onefragmentactivity.SingleFragmentActivity;

public class ListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new RecipeListFragment();
    }
}
