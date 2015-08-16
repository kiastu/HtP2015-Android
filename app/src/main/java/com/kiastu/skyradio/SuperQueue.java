package com.kiastu.skyradio;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by dakong on 8/15/15.
 */
public class SuperQueue {
    private static RequestQueue requestQueue;

    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public SuperQueue(Context context){
        requestQueue = Volley.newRequestQueue(context);
    }
}
