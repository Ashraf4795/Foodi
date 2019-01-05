package com.city.coding.restaurant3.Helper;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class requestQueueHelper {
    private static requestQueueHelper mRequestQueueHelper;
    private RequestQueue mRequestQueue;
    private static Context mContext;

    private requestQueueHelper(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized requestQueueHelper getInstance(Context context) {
        if (mRequestQueueHelper == null) {
            mRequestQueueHelper = new requestQueueHelper(context);
        }
        return mRequestQueueHelper;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(tag);
        getRequestQueue().add(req);
    }
}
