package com.city.coding.restaurant3.Activites;

import android.Manifest;
import android.app.LoaderManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.city.coding.restaurant3.Adapters.homeRecycleViewAdapter;
import com.city.coding.restaurant3.Model.voucherData;
import com.city.coding.restaurant3.R;
import com.city.coding.restaurant3.Retrofit.voucherLoader;
import com.google.android.gms.vision.barcode.Barcode;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;

import spencerstudios.com.bungeelib.Bungee;

public class homeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<voucherData>> {
    private String TAG = "homeActivity";
    private static boolean isOwner ;
    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int PERMISSION_REQUEST = 200;
    private static final String PERMISSION_TITLE = "Camera for scanning";
    private static final String PERMISSION_MESSAGE = "need permission to open Camera for scanning";
    private ArrayList<voucherData> voucherList;

    private SwipeRefreshLayout swipeRefreshLayout;
    // drawer items
    private PrimaryDrawerItem home, myVoucher, myProfile;
    private SecondaryDrawerItem reward, latestNews, stores, tc, logout;
    private ProgressBar progressBar;
    LoaderManager loaderManager = getLoaderManager();
    private String name, email;
    private RecyclerView recyclerView;
    private homeRecycleViewAdapter adapter;
    private MaterialSearchView searchView;
    private static final String USER_URL = "http://honeydewpos.com/loycher/api/voucher_list.php?user_id=";
    private Intent intent;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        swipeRefreshLayout = findViewById(R.id.swipHomeRefresher_id);
        progressBar = findViewById(R.id.progress_bar_id);
        progressBar.setVisibility(View.VISIBLE);
        searchView = findViewById(R.id.search_view);
        voucherList = new ArrayList<>();
        //get userId , sent by loginActivity
        intent = getIntent();
        userId = intent.getStringExtra("user_id");
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        isOwner = intent.getBooleanExtra("ownerState",false);
        Log.e(TAG, "onCreate: " + "user id =" + userId);
        //showToastMessWithUserId(userId);


        //set toolbar
        Toolbar toolbar = findViewById(R.id.toolBar_id);
        setSupportActionBar(toolbar);

        // build drawer in toolbar
        generateDrawer(toolbar);

        recyclerView = findViewById(R.id.recycelView_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        if (isNetworkAvailable()) {
            Log.e(TAG, "onCreate: " + "network is connected");
            intiLoader();
        } else {
            Toast.makeText(homeActivity.this, "no connection", Toast.LENGTH_LONG).show();
        }

        //set swip refresh listener
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                intiLoader();
            }
        });

        //searchView query listeners
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
    }


    private void showToastMessWithUserId(String userId) {
        if (!TextUtils.isEmpty(userId)) {
            Toast.makeText(homeActivity.this, "userId " + userId, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(homeActivity.this, "Try to signIn again", Toast.LENGTH_LONG).show();

        }
    }


    @Override
    public android.content.Loader<ArrayList<voucherData>> onCreateLoader(int id, Bundle args) {
        Log.e(TAG, "onCreateLoader: make connection to " + USER_URL + userId);
        return new voucherLoader(homeActivity.this, USER_URL + userId);

    }

    @Override
    public void onLoadFinished(android.content.Loader<ArrayList<voucherData>> loader, ArrayList<voucherData> data) {
        voucherList = data;
        adapter = new homeRecycleViewAdapter(homeActivity.this, data, userId);
        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.INVISIBLE);

        //swip refresh disable
        swipeRefreshLayout.setRefreshing(false);
        if (data != null) {
            Log.e(TAG, "onLoadFinished: " + "data size =" + data.size());
            for (int i = 0; i < data.size(); i++)
                Log.e(TAG, "onLoadFinished: " + data.get(i).getVoucherId() + "\n");
        } else {
            Log.e(TAG, "onLoadFinished: " + "data size = null");
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<ArrayList<voucherData>> loader) {

    }

    private void intiLoader() {
        loaderManager.initLoader(0, null, this);
        Log.e("Initialize loader ", " done ");
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    //inflate client_menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if (isOwner()) {
            inflater.inflate(R.menu.owner_menu, menu);
            MenuItem item = menu.findItem(R.id.search);
            searchView.setMenuItem(item);
            return super.onCreateOptionsMenu(menu);
        } else {
            inflater.inflate(R.menu.client_menu, menu);
            MenuItem item = menu.findItem(R.id.search);
            searchView.setMenuItem(item);
            return super.onCreateOptionsMenu(menu);
        }
    }//end

    /*set menu item listener*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.scan:
                Log.e("#scan", "onOptionsItemSelected: done");
                moveToScanner2Permission();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public static boolean isOwner() {
        return isOwner;
    }

    public static void setIsOwner(boolean isOwner) {
        homeActivity.isOwner = isOwner;
    }


    //request permissioin
    private void requestpermission() {
        Log.e("requestPermission", "requestpermission: ");
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);

    }
    //end

    //request permission 2
    private void requestPermission2() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {

            new AlertDialog.Builder(this)
                    .setTitle("Camera Permission needed")
                    .setMessage("This permission is needed to Open Camera")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(homeActivity.this,
                                    new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        }
    }


    //move to scanner screen and ask for permission
    private void moveToScanner() {
        Intent intent = new Intent(this, scannerActivity.class);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Log.e("should request", "moveToScanner: ");
            requestpermission();
            startActivityForResult(intent, PERMISSION_REQUEST);
            Bungee.zoom(this);
        } else {
            Log.e("should NOT request", "moveToScanner: ");

            startActivityForResult(intent, PERMISSION_REQUEST);
            Bungee.zoom(homeActivity.this);
        }
    }

    //move to scanner 2
    public void moveToScanner2Permission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermission2();
            } else {
                callScanner();
            }
        } else {
            callScanner();
        }
    }

    //check result that came from scanner activity after scanning
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PERMISSION_REQUEST) {
            if (data != null) {
                Barcode barCode = data.getParcelableExtra("barCode");
                //Log.e("#result", barCode.displayValue );
                Toast.makeText(this, barCode.displayValue, Toast.LENGTH_LONG).show();
            }
        }
    }

    //call scanner activity
    private void callScanner() {
        Intent intent = new Intent(this, scannerActivity.class);
        startActivityForResult(intent, PERMISSION_REQUEST);
        Bungee.zoom(this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callScanner();
                }
        }
    }


    // generate drawer method
    private void generateDrawer(Toolbar toolbar) {
        drawerItems();
        new DrawerBuilder().withActivity(this).withToolbar(toolbar)
                .addDrawerItems(
                        myVoucher,
                        myProfile,
                        new DividerDrawerItem(),
                        reward,
                        latestNews,
                        stores,
                        new DividerDrawerItem(),
                        tc,
                        new DividerDrawerItem(),
                        logout

                ).withAccountHeader(createDrawerProfileHeader(this.name, this.email))

                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        switch (position) {
                            case 1:
                                Intent favIntent = new Intent(homeActivity.this, favActivity.class);
                                intent.putExtra("user_id", userId);
                                startActivity(favIntent);
                                // Toast.makeText(homeActivity.this, "My Voucher", Toast.LENGTH_LONG).show();
                                break;
                            case 2:
                                Intent profileInent = new Intent(homeActivity.this, user_profile.class);
                                startActivity(profileInent);
                                // Toast.makeText(homeActivity.this, "My Profile", Toast.LENGTH_LONG).show();
                                break;
                            case 4:
                                Intent rewardIntent = new Intent(homeActivity.this, reward.class);
                                startActivity(rewardIntent);
                                //Toast.makeText(homeActivity.this, "Rewards", Toast.LENGTH_LONG).show();
                                break;
                            case 5:
                                Intent newsIntent = new Intent(homeActivity.this, news.class);
                                startActivity(newsIntent);
                                // Toast.makeText(homeActivity.this, "latest news", Toast.LENGTH_LONG).show();
                                break;
                            case 6:
                                Intent storesIntent = new Intent(homeActivity.this, storesActivity.class);
                                startActivity(storesIntent);
                                finish();
                                // Toast.makeText(homeActivity.this, "Stores", Toast.LENGTH_LONG).show();
                                break;
                            case 8:
                                Intent tcIntent = new Intent(homeActivity.this, term_condition.class);
                                startActivity(tcIntent);
                                finish();
                                // Toast.makeText(homeActivity.this, "term and condition", Toast.LENGTH_LONG).show();
                                break;
                            case 10:
                                removeDataFromPreference();
                                Intent logoutIntent = new Intent(homeActivity.this, splash.class);
                                startActivity(logoutIntent);
                                finish();
                                // Toast.makeText(homeActivity.this, "term and condition", Toast.LENGTH_LONG).show();
                                break;

                        }
                        return true;
                    }
                }).withDrawerGravity(Gravity.START)
                .build();
    }

    private void removeDataFromPreference() {
        SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("Unm");
        editor.remove("Psw");
        editor.remove("user_id");
        editor.apply();
    }

    //create account header into drawer
    protected AccountHeader createDrawerProfileHeader(String name, String email) {
        AccountHeader accountHeader = new AccountHeaderBuilder()
                .withActivity(homeActivity.this)
                .withHeaderBackground(R.drawable.background)
                .addProfiles(
                        new ProfileDrawerItem().withName(name).withEmail(email)
                                .withIcon(getResources().getDrawable(R.drawable.ic_user))
                ).withOnAccountHeaderItemLongClickListener(new AccountHeader.OnAccountHeaderItemLongClickListener() {
                    @Override
                    public boolean onProfileLongClick(View view, IProfile profile, boolean current) {
                        return false;
                    }
                }).build();
        return accountHeader;
    }

    // assign items with name and icons
    private void drawerItems() {
        home = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.drawerHome).withIcon(R.drawable.home);
        myVoucher = new PrimaryDrawerItem().withIdentifier(2).withName(R.string.myVoucher).withIcon(R.drawable.my_voucher);
        myProfile = new PrimaryDrawerItem().withIdentifier(3).withName(R.string.myProfile).withIcon(R.drawable.my_profile);
        reward = new SecondaryDrawerItem().withIdentifier(4).withName(R.string.rewards).withIcon(R.drawable.reward);
        latestNews = new SecondaryDrawerItem().withIdentifier(5).withName(R.string.latestNews).withIcon(R.drawable.latest_news);
        stores = new SecondaryDrawerItem().withIdentifier(6).withName(R.string.stores).withIcon(R.drawable.stores);
        tc = new SecondaryDrawerItem().withIdentifier(7).withName(R.string.TC).withIcon(R.drawable.tc);
        logout = new SecondaryDrawerItem().withIdentifier(8).withName(R.string.logout).withIcon(R.drawable.logout);
    }


    //filter searchView
    private void filter(String text) {
        ArrayList<voucherData> filteredList = new ArrayList<>();
        for (voucherData item : voucherList) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if (adapter != null)
            adapter.filteredList(filteredList);
        
            //Toast.makeText(homeActivity.this,"adapter is null ",Toast.LENGTH_LONG).show();
    }

}

