package com.caine.allan.improvedrecipefinder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.caine.allan.improvedrecipefinder.data.Recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by allancaine on 2015-10-26.
 */
public class RecipeListFragment extends Fragment implements DataManager.RecipeSearchListener{
    private DataManager mDataManager;
    private List<Recipe> mRecipes;
    private RecyclerView mRecyclerView;
    private RecipeListAdapter mRecipeListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recipe_List_Recycler_View);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecipeListAdapter = new RecipeListAdapter(getActivity(), new ArrayList<Recipe>());
        mRecyclerView.setAdapter(mRecipeListAdapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mDataManager = DataManager.get(getActivity());
        mDataManager.addRecipeSearchListener(this);
        mDataManager.fetchRecipes();
    }

    @Override
    public void onStop() {
        super.onStop();
        mDataManager.removeRecipeSearchListener(this);
    }

    @Override
    public void onSearchComplete() {
        mRecipes = mDataManager.getRecipes();
        mRecipeListAdapter.setRecipeList(mRecipes);
        mRecipeListAdapter.notifyDataSetChanged();
    }
}
