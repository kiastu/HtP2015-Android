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
//        super.onPushReceive(context,intent);
        Intent newIntent = new Intent(context,MainActivity.class);
        newIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        Log.d("ParseReceiver","Intent fired");
        context.startActivity(newIntent);
    }
}


