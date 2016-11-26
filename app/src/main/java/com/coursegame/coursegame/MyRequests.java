package com.coursegame.coursegame;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by disha on 21-11-2016.
 */

public class MyRequests {
    private static MyRequests mInstance;
    Context mCtx;
    RequestQueue mRequestQueue;

    private MyRequests(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();

    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public static synchronized MyRequests getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MyRequests(context);
        }
        return mInstance;
    }
}
