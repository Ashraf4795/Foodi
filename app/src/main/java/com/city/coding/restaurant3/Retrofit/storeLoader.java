package com.city.coding.restaurant3.Retrofit;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.city.coding.restaurant3.Helper.storeQueryUtils;
import com.city.coding.restaurant3.Model.store;

import java.util.ArrayList;


public class storeLoader extends AsyncTaskLoader<ArrayList<store>> {
    private static final String TAG = "storeLoader";
    String Url;
    ArrayList<store> listOfStore;

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    public storeLoader(@NonNull Context context, String url) {
        super(context);
        Url = url;
    }

    @Override
    public ArrayList<store> loadInBackground() {
        listOfStore = new ArrayList<store>();
        if (Url != null && !TextUtils.isEmpty(Url)) {
            listOfStore = storeQueryUtils.fetchData(Url);
            if (listOfStore.size() > 0) {
                Log.e(TAG, "loadInBackground: " + listOfStore.size());
            } else {
                Log.e(TAG, "loadInBackground: list is empty");
            }
            return listOfStore;
        } else
            return null;
    }
}
