package com.city.coding.restaurant3.Activites;

import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.LoaderManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.city.coding.restaurant3.Adapters.storeAdapter;
import com.city.coding.restaurant3.Model.store;
import com.city.coding.restaurant3.R;
import com.city.coding.restaurant3.Retrofit.storeLoader;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

public class storesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<store>> {
    private static final String url = "http://honeydewpos.com/loycher/api/get_store_list";
    private static final String TAG = "storesActivity";
    private LoaderManager loaderManager;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private storeAdapter adapter;
    private ArrayList<store> storeList;
    private MaterialSearchView searchView;
    ArrayList<String> storeName = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores);
        storeList = new ArrayList<>();
        swipeRefreshLayout = findViewById(R.id.stores_swipHomeRefresher_id);
        progressBar = findViewById(R.id.stores_progress_bar_id);
        progressBar.setVisibility(View.VISIBLE);
        searchView = findViewById(R.id.search_view);
        loaderManager = getLoaderManager();
        recyclerView = findViewById(R.id.stores_recycelView_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        //set toolbar
        Toolbar toolbar = findViewById(R.id.stores_toolBar_id);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        if (isNetworkAvaliable()) {
            initLoader();
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initLoader();
            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.e(TAG, "onQueryTextSubmit: " + query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.e(TAG, "onQueryTextChange: " + newText);
                filter(newText);
                return false;
            }
        });
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                Log.e(TAG, "onSearchViewShown: ");
            }

            @Override
            public void onSearchViewClosed() {
                Log.e(TAG, "onSearchViewClosed: ");
            }
        });
    }


    @Override
    public Loader<ArrayList<store>> onCreateLoader(int id, Bundle args) {
        return new storeLoader(storesActivity.this, url);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<store>> loader, ArrayList<store> data) {

        storeList = data;
        if (storeList != null)
            Log.e(TAG, "onLoadFinished: " + " storeList size is " + storeList.size());
        else
            Log.e(TAG, "onLoadFinished: " + "storeList is null");
        progressBar.setVisibility(View.INVISIBLE);
        swipeRefreshLayout.setRefreshing(false);
        adapter = new storeAdapter(storesActivity.this, data);
        recyclerView.setAdapter(adapter);

    }


    @Override
    public void onLoaderReset(Loader<ArrayList<store>> loader) {

    }


    //initial loader
    private void initLoader() {
        loaderManager.initLoader(0, null, this);
        Log.e("initLoader ", " done");
    }

    //check for the internet connections before initialize loaderManager
    private boolean isNetworkAvaliable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return networkInfo != null && networkInfo.isConnected();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_view, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    //filter method for stores search
    private void filter(String newText) {
        ArrayList<store> filteredList = new ArrayList<>();
        for (store item : storeList) {
            if (item.getStore_name().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(item);
            }
        }
        //Log.e(TAG, "filter: "+"filteredList size is "+filteredList.size());
        if (adapter != null)
            adapter.filteredList(filteredList);
        //Log.e(TAG, "filter: "+" adapter item count "+adapter.getItemCount() );
    }
}
