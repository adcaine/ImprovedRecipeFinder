package com.caine.allan.improvedrecipefinder.data;


import com.google.gson.annotations.SerializedName;


/**
 * Created by allancaine on 2015-10-26.
 */
public class Recipe {

    private static final String TAG = "RecipeItem";

    @SerializedName("publisher")     private String mPublisher;
    @SerializedName("f2f_url")       private String mf2fURL;
    @SerializedName("title")         private String mTitle;
    @SerializedName("source_url")    private String mSourceURL;
    @SerializedName("recipe_id")     private String mRecipeId;
    @SerializedName("image_url")     private String mImageURL;
    @SerializedName("social_rank")   private double mSocialRank;
    @SerializedName("publisher_url") private String mPublisherURL;


    public String getTitle() {
        return mTitle;
    }

    public String getSourceURL() {
        return mSourceURL;
    }
}
