package com.kiastu.skyradio;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.util.Log;

import com.parse.ParsePush;
import com.parse.ParsePushBroadcastReceiver;

/**
 * Created by dakong on 8/15/15.
 */
public class ParseReceiver extends ParsePushBroadcastReceiver {

    @Override
    protected void onPushOpen(Context context, Intent intent) {
        Log.e("Push", "Push request opened");
        return;
    }

    @Override
    protected void onPushReceive(Context context, Intent intent){
        super.onPushReceive(context,intent);
        Intent newIntent = new Intent(context,MainActivity.class);
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        context.sendBroadcast(newIntent);
        return;
    }
}


