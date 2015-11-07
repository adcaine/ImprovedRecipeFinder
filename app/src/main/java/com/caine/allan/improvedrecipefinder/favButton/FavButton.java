package com.caine.allan.improvedrecipefinder.favButton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.caine.allan.improvedrecipefinder.R;

/**
 * Created by allancaine on 2015-11-06.
 */
public class FavButton extends FrameLayout {

    boolean isPressed;

    ImageView mImageView;

    Drawable mDrawableOn;
    Drawable mDrawableOff;

    ToggleListener mToggleListener;


    public FavButton(Context context) {
        this(context, null);
    }

    public FavButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        isPressed = false;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FavButton, 0, 0);
        mDrawableOn = array.getDrawable(0);
        mDrawableOff = array.getDrawable(1);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.fav_button, this, true);
        mImageView = (ImageView)view.findViewById(R.id.star_image_view);
        mImageView.setImageDrawable(mDrawableOff);
        mImageView.setVisibility(VISIBLE);
        mImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isPressed = !isPressed;
                mImageView.setImageDrawable(isPressed ? mDrawableOn : mDrawableOff);
                if (mToggleListener != null) {
                    mToggleListener.onToggle(isPressed);

                }
            };
        });
    }

    public void setToggleListener(ToggleListener toggleListener){
        mToggleListener = toggleListener;
    }

    public interface ToggleListener{
        void onToggle(boolean newState);
    }


}
