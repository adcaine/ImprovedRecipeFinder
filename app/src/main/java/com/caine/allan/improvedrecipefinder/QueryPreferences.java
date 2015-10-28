package com.caine.allan.improvedrecipefinder;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by allancaine on 2015-10-18.
 */
public class QueryPreferences {

    private static final String PREF_SEARCH_QUERY = "searchQuery";

    private QueryPreferences(){}

    public static String getStoredQuery(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_SEARCH_QUERY, null);
    }

    public static void setStoredQuery(Context context, String query){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_SEARCH_QUERY, query)
                .apply();
    }

    public static void clearStoredQuery(Context context){
        setStoredQuery(context, null);
    }
}
