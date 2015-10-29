package com.caine.allan.improvedrecipefinder;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.caine.allan.improvedrecipefinder.data.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by allancaine on 2015-10-26.
 */
public class RecipeListFragment extends Fragment implements DataManager.RecipeSearchListener{
    private static final String TAG = "RecipeListFragment";
    private static final String DIALOG_ABOUT = "dialog_about";
    private DataManager mDataManager;
    private List<Recipe> mRecipes;
    private RecyclerView mRecyclerView;
    private RecipeListAdapter mRecipeListAdapter;

    private ProgressDialog mDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recipe_List_Recycler_View);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecipeListAdapter = new RecipeListAdapter(getActivity(), new ArrayList<>());
        mRecyclerView.setAdapter(mRecipeListAdapter);
        setupLoadingDialog();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        mDataManager = DataManager.get(getActivity().getApplication());
        mDataManager.addRecipeSearchListener(this);
        updateItems();
    }

    @Override
    public void onStop() {
        super.onStop();
        mDataManager.removeRecipeSearchListener(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.recipe_finder_fragment, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_item_search);

        final SearchView searchView = (SearchView)menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                QueryPreferences.setStoredQuery(getActivity(), query);
                updateItems();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchClickListener(v -> {
                String query = QueryPreferences.getStoredQuery(getActivity());
                searchView.setQuery(query, false);
            });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_item_clear:
                QueryPreferences.clearStoredQuery(getActivity());
                updateItems();
                return true;
            case R.id.menu_refresh:
                updateItems();
                return true;
            case R.id.menu_item_attribution:
                FragmentManager fm = getFragmentManager();
                AboutDialog aboutDialog = new AboutDialog();
                aboutDialog.show(fm, DIALOG_ABOUT);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateItems() {
        String query = QueryPreferences.getStoredQuery(getActivity());
        mDataManager.fetchRecipes(query)
                .compose(loadingTransformer())
                .subscribe(recipeSearchResponse -> {
                    mRecipes = recipeSearchResponse.getRecipes();
                    mRecipeListAdapter.setRecipeList(mRecipes);
                    mRecipeListAdapter.notifyDataSetChanged();
                    if(mRecipes.size() == 0){
                        Toast.makeText(getActivity(),R.string.no_results, Toast.LENGTH_SHORT).show();
                    }
                },
                        error -> Log.e(TAG, "Cannot get a response :" + error));
    }

    private void setupLoadingDialog(){
        mDialog = new ProgressDialog(getActivity());
        mDialog.setIndeterminate(true);
        mDialog.setMessage(getString(R.string.loading));
    }

    protected <T> rx.Observable.Transformer<T, T> loadingTransformer(){
        return observable -> observable.doOnSubscribe(mDialog::show)
                .doOnCompleted(() -> {
                    if(mDialog != null && mDialog.isShowing()) {
                        mDialog.dismiss();
                    }
                });
    }


    @Override
    public void onSearchComplete() {

    }
}
