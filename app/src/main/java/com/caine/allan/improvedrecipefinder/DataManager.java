package com.caine.allan.improvedrecipefinder;

import android.content.Context;
import android.util.Log;

import com.caine.allan.improvedrecipefinder.data.Recipe;
import com.caine.allan.improvedrecipefinder.data.RecipeInterface;
import com.caine.allan.improvedrecipefinder.data.RecipeSearchResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Created by allancaine on 2015-10-26.
 */
public class DataManager {

    private static final String TAG = "DataManager";

    private static final String API_KEY = BuildConfig.API_KEY;
    private static final String FOOD2FORK_ENDPOINT = "http://food2fork.com/api";
    private static final String TEST_SEARCH = "chicken noodle soup";
    private List<Recipe> mRecipes;
    private List<RecipeSearchListener> mRecipeSearchListeners;

    private RestAdapter mRestAdapter;
    private RecipeInterface mRecipeInterface;
    private static DataManager sDataManager;

    protected DataManager(RestAdapter restAdapter){
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
                    .setRequestInterceptor(new RecipeRequestInterceptor())
                    .build();

            sDataManager = new DataManager(restAdapter);
        }
        return sDataManager;
    }

    public void fetchRecipes(){
        mRecipeInterface.recipeSearch(TEST_SEARCH, new CallBackHandler());
    }

    public List<Recipe> getRecipes() {
        return mRecipes;
    }

    private class CallBackHandler implements Callback<RecipeSearchResponse>{
        @Override
        public void success(RecipeSearchResponse recipeSearchResponse, Response response) {
            mRecipes = recipeSearchResponse.getRecipes();
            notifySearchListeners();
        }

        @Override
        public void failure(RetrofitError error) {
            Log.e(TAG, "Failed to get respose: " + error);
        }
    }

    private static class RecipeRequestInterceptor implements RequestInterceptor{
        @Override
        public void intercept(RequestFacade request) {
            request.addQueryParam("key", API_KEY);
        }
    }

    public interface RecipeSearchListener {
        void onSearchComplete();
    }

    public void addRecipeSearchListener(RecipeSearchListener listener){
        mRecipeSearchListeners.add(listener);
    }

    public void removeRecipeSearchListener(RecipeSearchListener listener){
        mRecipeSearchListeners.remove(listener);
    }

    private void notifySearchListeners(){
        for(RecipeSearchListener listener : mRecipeSearchListeners){
            listener.onSearchComplete();
        }
    }




}
