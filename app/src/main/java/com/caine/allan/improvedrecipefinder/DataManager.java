package com.caine.allan.improvedrecipefinder;

import android.content.Context;

import com.caine.allan.improvedrecipefinder.data.Recipe;
import com.caine.allan.improvedrecipefinder.data.RecipeInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;


import retrofit.RestAdapter;

import retrofit.converter.GsonConverter;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by allancaine on 2015-10-26.
 */
public class DataManager {

    private static final String TAG = "DataManager";

    private static final String FOOD2FORK_ENDPOINT = "http://food2fork.com/api";
    private List<Recipe> mRecipes;
    private List<RecipeSearchListener> mRecipeSearchListeners;

    private static RestAdapter sRestAdapter;
    private static RecipeInterface sRecipeInterface;
    private static DataManager sDataManager;


    private Context mContext;

    protected DataManager(Context context){
        mContext = context;
        mRecipeSearchListeners = new ArrayList<>();
        mRecipes = new ArrayList<>();

    }

    public static DataManager get(Context context){
        if(sDataManager == null){
            Gson gson = new GsonBuilder().create();
            sRestAdapter = new RestAdapter.Builder()
                    .setEndpoint(FOOD2FORK_ENDPOINT)
                    .setConverter(new GsonConverter(gson))
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setRequestInterceptor(facade -> facade.addQueryParam("key", BuildConfig.API_KEY))
                    .build();
            sRecipeInterface = sRestAdapter.create(RecipeInterface.class);
            sDataManager = new DataManager(context);
        }
        return sDataManager;
    }

    public void fetchRecipes(String query){
        sRecipeInterface.recipeSearch(query)
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
