package com.itexico.unittestingsample.service;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.itexico.unittestingsample.LoginActivity;
import com.itexico.unittestingsample.R;

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
            sendNotification(action);
        }
    }
    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    private void sendNotification(String action) {
        Log.d(TAG, "Sending notification");
        NotificationManagerCompat notificationManager=  NotificationManagerCompat.from(this);
//        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        //set-up the action for authorizing the action
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notifiaction)
                .setContentTitle(getString(R.string.app_name))
                .setAutoCancel(Boolean.TRUE)
                .setContentText("Notification message" + action);
        builder.setContentIntent(pendingIntent);
        notificationManager.notify(NOTIFICATION_TAG, NOTIFICATION_ID, builder.build());
    }
}
