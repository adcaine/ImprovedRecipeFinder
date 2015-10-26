package com.caine.allan.improvedrecipefinder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by allancaine on 2015-10-26.
 */
public class RecipeView extends LinearLayout{

    private TextView mTitleTextView;

    public RecipeView(Context context) {
        this(context, null);
    }

    public RecipeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        LinearLayout.LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(0, 0, 0, 16);
        setLayoutParams(params);

        LayoutInflater inflater = LayoutInflater.from(context);
        RecipeView view = (RecipeView)inflater.inflate(R.layout.view_recipe, this, true);
        mTitleTextView = (TextView)view.findViewById(R.id.view_recipe_title);
    }

    public void setTitleTextView(String title){
        mTitleTextView.setText(title);
    }
}
