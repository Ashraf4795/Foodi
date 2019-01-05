package com.city.coding.restaurant3.Activites;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.app.LoaderManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.city.coding.restaurant3.Adapters.favAdapter;
import com.city.coding.restaurant3.Helper.requestQueueHelper;
import com.city.coding.restaurant3.Model.voucherData;
import com.city.coding.restaurant3.R;
import com.city.coding.restaurant3.Retrofit.voucherLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class favActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<voucherData>> {

    private String TAG = "favActivity";
    private RecyclerView recyclerView;
    private favAdapter adapter;
    private LoaderManager loaderManager;
    private static String URL = "http://honeydewpos.com/loycher/api/get_saved_list.php?user_id=";
    protected String userId;
    ArrayList<voucherData> data;
    ProgressDialog progressDialog;
    ProgressBar progressBar;
    SwipeRefreshLayout swipeRefreshLayout;
    private Intent intent;
    private ActionBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);
        bar = getSupportActionBar();
        if(bar != null){
            bar.setElevation(0);
            bar.setDisplayHomeAsUpEnabled(true);
        }
        swipeRefreshLayout = findViewById(R.id.swipFavRefresher_id);
        progressBar = findViewById(R.id.progress_bar_id);
        progressBar.setVisibility(View.VISIBLE);
        /*progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);*/

        data = new ArrayList<>();
        /*get user id
        intent = getIntent();
        userId = intent.getStringExtra("user_id");
        Log.e(TAG, "onCreate: " + "user id = " + userId);
        Log.e(TAG, "onCreate: " + "intent get extra " + intent.getStringExtra("user_id"));
        Toast.makeText(favActivity.this, "user id " + userId, Toast.LENGTH_LONG).show();*/

        //get user id from shared pref
        userId = getUserIdFromSharedPreference();
        Log.e(TAG, "onCreate: " + "user id from pref =" + userId);
        //init loaderManager
        loaderManager = getLoaderManager();
        Log.e(TAG, "onCreate: " + "get Loader manager done");

        //init recyclerView
        recyclerView = findViewById(R.id.favRecycelView_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        //initLoader();

        if (isNetworkAvaliable()) {
            Log.e(TAG, "onCreate: " + "network is connected and loader is initalized");
            initLoader();
        } else {
            Toast.makeText(favActivity.this, "no connection", Toast.LENGTH_LONG).show();

        }
    //set swip refresh listener
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initLoader();
            }
        });

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


    //loader method
    @Override
    public android.content.Loader<ArrayList<voucherData>> onCreateLoader(int id, Bundle args) {
        //progressDialog.setMessage("Loading...");
        //showDialog();
        swipeRefreshLayout.setColorSchemeResources(R.color.master_blue);
        return new voucherLoader(favActivity.this, URL + userId);
    }

    @Override
    public void onLoadFinished(android.content.Loader<ArrayList<voucherData>> loader, ArrayList<voucherData> data) {
        //hideDialog();
        if (data != null) {
            Log.e(TAG, "onLoadFinished: data size = " + data.size());
        } else {
            Log.e(TAG, "onLoadFinished: data size = null");

        }
        //send data to adapter to render on the rcyclerView
        adapter = new favAdapter(favActivity.this, data, userId);
        //set recyclerView adapter
        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.INVISIBLE);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(android.content.Loader<ArrayList<voucherData>> loader) {
        //TODO reset data
    }
    //end


    //initial loader
    private void initLoader() {
        loaderManager.initLoader(0, null, this);
        Log.e("initLoader ", " done");
    }


    //get list of fav using volley lib
    public ArrayList<voucherData> getFavList(final Context context, final String userId) {
        final ArrayList<voucherData> voucherFavList = new ArrayList<>();
        String cancel_req_tag = "favList";
        String Url = "http://honeydewpos.com/loycher/api/get_saved_list.php?user_id=";
        StringRequest strReq = new StringRequest(Request.Method.GET,
                Url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    Log.e("json response", response);
                    JSONObject jObj = new JSONObject(response);
                    if (!TextUtils.isEmpty(jObj.toString())) {
                        JSONArray favList = jObj.getJSONArray("list_of_voucher");
                        if (favList != null && favList.length() > 0) {
                            for (int i = 0; i < favList.length(); i++) {
                                JSONObject index = favList.getJSONObject(i);
                                voucherData v = getDataFromJson(index);
                                Log.e("voucher value", v.getTitle());
                                voucherFavList.add(v);
                            }
                            Log.e("voucherSize", voucherFavList.size() + "");
                        } else {
                            Toast.makeText(context, "list is empty", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(context, "no Response", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("request", "Login Error: " + error.getMessage());
                Toast.makeText(context,
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", userId);
                return params;
            }
        };
        // Adding request to request queue
        requestQueueHelper.getInstance(context).addToRequestQueue(strReq, cancel_req_tag);
        Log.e("voucherSize from", voucherFavList.size() + "");
        return voucherFavList;
    }

    //get Data From json
    private static voucherData getDataFromJson(JSONObject json) throws JSONException {
        String voucherId, voucherName, voucherQty, voucherValue, voucherImage;

        voucherId = json.getString("voucher_id");
        voucherName = json.getString("voucher_name");
        voucherQty = json.getString("voucher_qty");
        voucherValue = json.getString("voucher_value");
        voucherImage = json.getString("voucher_img");

        return new voucherData(voucherName, voucherQty, voucherValue, voucherImage, voucherId);
    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    //get user id from sharedPreference to make url connection using user id
    private String getUserIdFromSharedPreference() {
        SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
        Log.e(TAG, "getUserIdFromSharedPreference: " + "userid from preference = " + sp.getString("user_id", null));
        return sp.getString("user_id", null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
