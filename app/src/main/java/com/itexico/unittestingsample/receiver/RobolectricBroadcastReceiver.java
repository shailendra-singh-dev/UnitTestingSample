package com.itexico.unittestingsample.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.itexico.unittestingsample.service.RobolectricIntentService;

/**
 * Created by iTexico Developer on 8/11/2016.
 */
public class RobolectricBroadcastReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, RobolectricIntentService.class);
        service.putExtra("data", "receiver_data");
        //start the service which needs to handle the intent
        context.startService(service);
    }
}
