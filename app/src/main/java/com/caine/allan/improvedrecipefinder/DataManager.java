package com.caine.allan.improvedrecipefinder;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.caine.allan.improvedrecipefinder.data.Recipe;
import com.caine.allan.improvedrecipefinder.data.RecipeInterface;
import com.caine.allan.improvedrecipefinder.data.RecipeSearchResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;


import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

import retrofit.converter.GsonConverter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by allancaine on 2015-10-26.
 */
public class DataManager {

    private static final String TAG = "DataManager";

    private static final String API_KEY = BuildConfig.API_KEY;
    private static final String FOOD2FORK_ENDPOINT = "http://food2fork.com/api";
    private List<Recipe> mRecipes;
    private List<RecipeSearchListener> mRecipeSearchListeners;

    private RestAdapter mRestAdapter;
    private RecipeInterface mRecipeInterface;
    private static DataManager sDataManager;


    private Context mContext;

    protected DataManager(RestAdapter restAdapter, Context context){
        mContext = context;
        mRestAdapter = restAdapter;
        mRecipeSearchListeners = new ArrayList<>();
        mRecipes = new ArrayList<>();
        mRecipeInterface = mRestAdapter.create(RecipeInterface.class);
    }

    public static DataManager get(Context context){
        if(sDataManager == null){
            Gson gson = new GsonBuilder().create();
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(FOOD2FORK_ENDPOINT)
                    .setConverter(new GsonConverter(gson))
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setRequestInterceptor(facade -> facade.addQueryParam("key", API_KEY))
                    .build();

            sDataManager = new DataManager(restAdapter, context);
        }
        return sDataManager;
    }

    public void fetchRecipes(String query){
        mRecipeInterface.recipeSearch(query)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::notifySearchListenersOnSearchStart)
                .doOnCompleted(this::notifySearchListenersOnComplete)
                .subscribe(result -> mRecipes = result.getRecipes());
    }

    public List<Recipe> getRecipes(){
        return mRecipes;
    }

    public interface RecipeSearchListener {
        void onSearchStart();
        void onSearchComplete();
    }

    public void addRecipeSearchListener(RecipeSearchListener listener){
        mRecipeSearchListeners.add(listener);
    }

    public void removeRecipeSearchListener(RecipeSearchListener listener){
        mRecipeSearchListeners.remove(listener);
    }

    private void notifySearchListenersOnComplete(){
        for(RecipeSearchListener listener : mRecipeSearchListeners){
            listener.onSearchComplete();
        }
    }

    private void notifySearchListenersOnSearchStart(){
        for(RecipeSearchListener listener : mRecipeSearchListeners){
            listener.onSearchStart();
        }
    }
}
