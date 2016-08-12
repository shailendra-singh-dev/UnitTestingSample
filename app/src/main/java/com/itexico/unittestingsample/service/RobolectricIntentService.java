package com.itexico.unittestingsample.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by iTexico Developer on 8/11/2016.
 */
public class RobolectricIntentService extends IntentService {

    public static final String TAG = RobolectricIntentService.class.getSimpleName();
    public static final String NOTIFICATION_TAG = RobolectricIntentService.class.getSimpleName();
    public static final int NOTIFICATION_ID = 1000;

    public RobolectricIntentService() {
        super(TAG);
    }

    public RobolectricIntentService(String name) {
        super(name);
    }

    @Override
    public void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null && !extras.isEmpty()) {  // has effect of un parcelling Bundle
            Log.d(TAG, "Extras were found");
            String action = intent.getStringExtra("ACTION");
            Log.i(TAG,"onHandleIntent(),action:"+action);
            //Perform any background operation...
        }
    }



}
