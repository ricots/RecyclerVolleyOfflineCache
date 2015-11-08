package com.example.big.RecyclerVolleyOfflineCache.utils.network;

/**
 * Created by Vasileios on 6/11/2015.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.big.RecyclerVolleyOfflineCache.utils.storage.SaveImageSingleton;
import java.util.Map;

import static com.android.volley.toolbox.HttpHeaderParser.parseDateAsEpoch;

public class MySingleton {
    private static MySingleton mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Context mCtx;


    private MySingleton(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20); //20 items in cache

                    @Override
                    public Bitmap getBitmap(String url) {
                        // attempt to get bitmap from temp cache
                        Bitmap s = cache.get(url);

                        // if the temp cache is null, look further in the file cache
                        if (s==null){
                            s = SaveImageSingleton.getInstance(mCtx).getImage(url);
                        }
                        return s;
                    }


                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        // put in the file cache
                        SaveImageSingleton.getInstance(mCtx).saveImage(url,bitmap);
                        // put in the temp cache
                        cache.put(url, bitmap);
                    }
                });
    }

    public static synchronized MySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            Context c = mCtx.getApplicationContext();
            mRequestQueue = Volley.newRequestQueue(c);
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    //TODO: Symazema
    public static Cache.Entry parseCacheHeaders(NetworkResponse response) {
        long now = System.currentTimeMillis();

        Map<String, String> headers = response.headers;

        long serverDate = 0;
        long serverExpires = 0;
        long softExpire = 0;
        long maxAge = 0;
        boolean hasCacheControl = false;

        String serverEtag = null;
        String headerValue;

        headerValue = headers.get("Date");
        if (headerValue != null) {
            serverDate = parseDateAsEpoch(headerValue);
        }


        headerValue = headers.get("Cache-Control");
        if (headerValue != null) {
            hasCacheControl = true;
            String[] tokens = headerValue.split(",");
//            for (int i = 0; i < tokens.length; i++) {
//                String token = tokens[i].trim();
//                if (token.equals("no-cache") || token.equals("no-store")) {
//                    return null;
//                } else if (token.startsWith("max-age=")) {
//                    try {
//                        maxAge = Long.parseLong(token.substring(8));
//                    } catch (Exception e) {
//                    }
//                } else if (token.equals("must-revalidate") || token.equals("proxy-revalidate")) {
//                    maxAge = 0;
//                }
//            }

            //inject!
            maxAge = Long.parseLong("86400");
        }

        headerValue = headers.get("Expires");
        //inject!
        //TODO: make this system time plus 6 months or so
        headerValue = "Mon, 18 Sep 2017  20:20:20 GMT";
        if (headerValue != null) {
            serverExpires = parseDateAsEpoch(headerValue);
        }

        serverEtag = headers.get("ETag");

        // Cache-Control takes precedence over an Expires header, even if both exist and Expires
        // is more restrictive.
        if (hasCacheControl) {
            softExpire = now + maxAge * 1000;
        } else if (serverDate > 0 && serverExpires >= serverDate) {
            // Default semantic for Expire header in HTTP specification is softExpire.
            softExpire = now + (serverExpires - serverDate);
        }

        Cache.Entry entry = new Cache.Entry();
        entry.data = response.data;
        entry.etag = serverEtag;
        entry.softTtl = softExpire;
        entry.ttl = entry.softTtl;
        entry.serverDate = serverDate;
        entry.responseHeaders = headers;

        return entry;
    }


}
