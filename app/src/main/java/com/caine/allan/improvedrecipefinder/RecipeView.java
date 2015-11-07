package com.caine.allan.improvedrecipefinder;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.caine.allan.improvedrecipefinder.favButton.FavButton;

/**
 * Created by allancaine on 2015-10-26.
 */
public class RecipeView extends LinearLayout{

    private TextView mTitleTextView;
    private FavButton mFavButton;

    public RecipeView(Context context) {
        this(context, null);
    }

    public RecipeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);
        LinearLayout.LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(0, 0, 0, 16);
        setLayoutParams(params);

        LayoutInflater inflater = LayoutInflater.from(context);
        RecipeView view = (RecipeView)inflater.inflate(R.layout.view_recipe, this, true);
        mTitleTextView = (TextView)view.findViewById(R.id.view_recipe_title);
        mFavButton = (FavButton)view.findViewById(R.id.list_item_fav_button);
    }

    public void setTitleTextView(String title){
        mTitleTextView.setText(title);
    }

    public FavButton getFavButton() {
        return mFavButton;
    }

    @Override
    public String toString() {
        return mTitleTextView.getText().toString();
    }
}
