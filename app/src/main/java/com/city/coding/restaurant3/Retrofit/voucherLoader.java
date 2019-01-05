package com.city.coding.restaurant3.Retrofit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.content.AsyncTaskLoader;
import android.util.Log;

import com.city.coding.restaurant3.Helper.voucherQueryUtile;
import com.city.coding.restaurant3.Model.voucherData;

import java.util.ArrayList;

public class voucherLoader extends AsyncTaskLoader<ArrayList<voucherData>> {
    String Url = "" ;



    @Override
    protected void onStartLoading() {
        forceLoad();;
    }

    public voucherLoader(@NonNull Context context , String url) {
        super(context);
        Url = url;
    }

    @Nullable
    @Override
    public ArrayList<voucherData> loadInBackground() {
        if(Url == null){return null;}
        Log.e("loadInBackground" ,Url );
        ArrayList<voucherData> voucherData = voucherQueryUtile.fetchData(Url);
       if(voucherData!=null) {
           Log.e("data size", "loadInBackground: " + voucherData.size());
       }
        return voucherData;
    }


}
