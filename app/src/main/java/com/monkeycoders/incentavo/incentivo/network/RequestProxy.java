package com.monkeycoders.incentavo.incentivo.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class RequestProxy {
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    RequestProxy(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
        mImageLoader = new ImageLoader(this.mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);

            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }

            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });
    }

    public ImageLoader getImageLoader() {
        return this.mImageLoader;
    }

    public void callPostService(String url, JSONObject params, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener, String tag) {
        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.POST, url, params, listener, errorListener);
        myReq.setTag(tag);
        myReq.setRetryPolicy(new DefaultRetryPolicy(10000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(myReq);
    }
}