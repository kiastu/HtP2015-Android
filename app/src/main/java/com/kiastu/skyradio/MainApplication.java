package com.kiastu.skyradio;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.SaveCallback;


/**
 * Created by dakong on 8/15/15.
 */
public class MainApplication extends Application {
    private static String APP_ID = "tvBeInNQqHmjKJXP2gaaS3LVENcqSFhZyUL1rGrJ";
    private static String CLIENT_ID = "H972ZNgtz6OZVIdyBYUn7EmpHnxHKfuMqU0HgVRh";
    //    public static String HOME_BASE = "http://allupinyour.space";
    public static String HOME_BASE = "http://e6c30700.ngrok.io";


    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, APP_ID, CLIENT_ID);
        //subscribe
        ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });
        new SuperQueue(this);//initialize the superqueue
    }
}
